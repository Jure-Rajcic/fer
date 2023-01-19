package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import hr.fer.zemris.java.hw05.shell.ShellIOException;

/**
 * class Crypto serves for decrypting and encrypting and calculating digest from
 * files.
 * 
 * @author Jure Rajcic
 *
 */
public class Crypto {

	/**
	 * Method calcualtes the digest of the @param file with SHA-256 algorithm
	 */
	public static void checksha(String file) {
		try (
				InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(file)));
				Scanner sc = new Scanner(System.in);) {
			System.out.printf("Please provide expected sha-256 digest for " + file + ":\n> ");
			String expectedDigest = sc.nextLine().trim();
			byte[] buff = new byte[4 * 1024];
			MessageDigest sha = MessageDigest.getInstance("SHA-256");

			for (int r = is.read(buff); r != -1; r = is.read(buff)) {
				sha.update(buff, 0, r);
			}
			String calculatedDigest = Util.bytetohex(sha.digest());
			String output;
			if (calculatedDigest.equals(expectedDigest.toLowerCase()))
				output = "Digesting completed. Digest of " + file + " matches expected digest.";
			else
				output = "Digesting completed. Digest of " + file + " does not match the excepted digest. Digest was: "
						+ calculatedDigest;
			System.out.println(output);
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method crypts or decrypts content of first file and stores it in another file
	 * @param encrypt   determents wheter to encrypt or decrypt content
	 * @param readFile  file from whic we read content
	 * @param writeFile file from whic we write content
	 */
	public static void crypt(boolean encrypt, String readFile, String writeFile) {

		try (
				InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(readFile)));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(writeFile)));
				Scanner sc = new Scanner(System.in);) {
			// <--code snipet from homework-->
			System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): \n> ");
			String keyText = sc.nextLine().trim();
			System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits): \n> ");
			String ivText = sc.nextLine().trim();
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			// <--code snipet from homework-->

			byte[] buff = new byte[4 * 1024];
			byte[] crypt;

			int r = is.read(buff);
			while (r == buff.length) {
				crypt = cipher.update(buff, 0, r);
				os.write(crypt);
				r = is.read(buff);
			}
			crypt = cipher.update(buff, 0, r);
			os.write(crypt);
			crypt = cipher.doFinal();
			os.write(crypt);
			System.out.println((encrypt ? "Encryption " : "Decryption ") + "completed. Generated file " + writeFile + " based on file " + readFile + ".");
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	// java -cp target/classes hr.fer.oprpp1.hw05.crypto.Crypto checksha hw05test.bin
	// java -cp target/classes hr.fer.oprpp1.hw05.crypto.Crypto encrypt hw05.pdf hw05.crypted.pdf
	// java -cp target/classes hr.fer.oprpp1.hw05.crypto.Crypto decrypt hw05.crypted.pdf hw05orig.pdf

	public static void main(String[] args) {
		// checksha("hw05test.bin");
		// crypt(true, "hw05.pdf", "hw05.crypted.pdf");
		// crypt(false, "hw05.crypted.pdf", "hw05orig.pdf");
		// crypt(false, "hw05test.bin", "hw05test.pdf");
		if (args == null || !(args.length == 2 || args.length == 3))
		throw new ShellIOException("program can only take 2 or 3 arguments");
		switch(args[0]) {
		case "checksha" -> checksha(args[1]);
		case "encrypt", "decrypt" -> crypt(args[0].equals("encrypt"), args[1],
		args[2]);
		default -> throw new ShellIOException("first argument ");
		}
	}
}
