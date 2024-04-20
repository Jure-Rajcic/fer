library ieee;
use ieee.std_logic_1164.all;

entity brojke is
 port (
 jr_left, jr_right, jr_up, jr_down, jr_center: in std_logic; -- ulazi ULX2S
 jr_code: out std_logic_vector(7 downto 0) -- izlaz
 );
end brojke;

architecture abrojke of brojke is


    signal btns: std_logic_vector(4 downto 0);
    
begin
	btns <= jr_down & jr_left & jr_center & jr_up & jr_right; -- ULX2S
	
    with btns select
    jr_code <=
 "00000000" when "00000",
 "00000000" when "10000",
 "00110011" when "01000",
 "00110101" when "00100",
 "00110000" when "00010",
 "00110110" when "00001",
 "00110011" when "11000",
 "00110101" when "10100",
 "00110110" when "10010",
 "00110011" when "10001",
 "--------" when others ; -- don't care (00)36536053
    
end abrojke;