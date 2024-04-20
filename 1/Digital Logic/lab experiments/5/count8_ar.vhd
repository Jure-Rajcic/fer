library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity count is
    port (
	clk_25m: in std_logic;
	btn_up, btn_center: in std_logic;
	led: out std_logic_vector(7 downto 0)
    );
end count;

architecture x of count is
    signal R: std_logic_vector(7 downto 0);
    signal clk_key: std_logic;

begin
	I_debouncer: entity work.debouncer port map (
        clk => clk_25m, key => btn_up, debounced => clk_key    
	);
	
 process (clk_key, btn_center) 
 
 begin
 
 -- R <= R + 1 when rising_edge(clk_key);
 -- R <= "0000000" when btn_center = '1';
 
 if rising_edge (clk_key) then 
 R <= R + 1;
 end if;
 
 if (btn_center) = '1' then 
 R <= "00000000";
 end if;
 
 end process;
   
   led <= R;
 
 
end x;
