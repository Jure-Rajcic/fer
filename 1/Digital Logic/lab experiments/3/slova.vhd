library ieee;
use ieee.std_logic_1164.all;

entity slova is
    port (
        btn_down, btn_left, btn_center, btn_up, btn_right: in std_logic;
	rs232_tx: out std_logic;
	clk_25m: in std_logic;
        led: out std_logic_vector(7 downto 0);
	sw: in std_logic_vector(3 downto 0)
    );
end slova;


architecture behavioral of slova is
    signal code: std_logic_vector(7 downto 0);
    signal btns: std_logic_vector(4 downto 0);
    signal jr_code: std_logic_vector(7 downto 0);
    signal code2: std_logic_vector(7 downto 0);
    


begin

    -- Koristite samo jedan izraz za grupiranje jednobitnih ulaznih
    -- signala (tipki) u visebitni signal btns, zavisno od razvojne
    -- plocice koju koristite (ULX2S ili ULX3S)

    btns <= btn_down & btn_left & btn_center & btn_up & btn_right; -- ULX2S
    -- btns <= btn_f1 & btn_left & btn_down & btn_up & btn_right; -- ULX3S

    with btns select
   code2 <=
 "00000000" when "00000",
 "00000000" when "10000",
 "01001010" when "01000",
 "01110101" when "00100",
 "01110010" when "00010",
 "01100101" when "00001",
 "01010010" when "11000",
 "01100001" when "10100",
 "01101010" when "10010",
 "01100011" when "10001",
 "--------" when others ; -- don't care
    
    brojke: entity work.brojke port map (
    jr_left => btn_left,
    jr_right => btn_right,
    jr_center => btn_center,
    jr_down => btn_down,
    jr_up => btn_up,
    jr_code => jr_code
    );
    
    
    with sw(0) select
	code <= 
	code2 when '0', 
	jr_code when '1';
	
    led <= code;


    serializer: entity serial_tx port map (
	clk => clk_25m, byte_in => code, ser_out => rs232_tx
    );
end;
