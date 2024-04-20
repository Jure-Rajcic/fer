library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity morse is
    port(
	clk, rst, l, r: in std_logic;
	m: out std_logic
    );
end morse;

architecture mealy of morse is
        
	constant s0: std_logic_vector(2 downto 0) := "000";
	constant s1: std_logic_vector(2 downto 0) := "001";
	constant s2: std_logic_vector(2 downto 0) := "010";
	constant s3: std_logic_vector(2 downto 0) := "011";
	constant s4: std_logic_vector(2 downto 0) := "100";
	constant s5: std_logic_vector(2 downto 0) := "101";
	constant s6: std_logic_vector(2 downto 0) := "110";
	constant s7: std_logic_vector(2 downto 0) := "111";

    signal R_state, R_timer: std_logic_vector(2 downto 0);
    
    signal new_state: std_logic_vector(2 downto 0);
    signal state_we: std_logic;

begin
    -- Timer, FSM state registers
    process(clk)
    begin
	if rising_edge(clk) then
	    if rst = '1' then
		R_state <= (others => '0');
		R_timer <= (others => '0');
	    elsif state_we = '1' then
		R_state <= new_state;
		R_timer <= (others => '0');
	        else
		R_timer <= std_logic_vector(signed(R_timer) + 1);
	    end if;
	end if;
	end process;

    -- FSM combinatorial logic: "X" = Morse _.._
    process(R_state, R_timer, l, r)
    begin
	-- Defaults, overriden later by case / when sections when appropriate
	new_state <= std_logic_vector(signed(R_state) + 1);
	state_we <= '1';
	m <= '0';

	case R_state is
	when s0 =>
	    if l = '1' AND r = '0' then
	    new_state <= s1;
	    elsif l = '0' AND r = '1' then
	    new_state <= s5;
	    else
	    new_state <= s0;
	    end if;
	
	when s1 =>
	   if R_timer /= "001" then
	   m <= '1';
	   state_we <= '0';
	   end if;
	when s2 | s3 =>
	   if R_timer /= "011" then
	   m <= '1';
	   state_we <= '0';
	   end if;
	   
	when s4 =>
	   if R_timer /= "011" and R_timer /= "100" then
	   m <= '1';
	   end if;
	   if R_timer /= "100" then
	   m <= '0';
	   end if;
	   new_state <= s0;
	   
	   
	when s5 =>
	   if R_timer /= "001" then
	   m <= '1';
	   state_we <= '0';
	   end if;
	when s6 =>
	   if R_timer /= "011" then
	   m <= '1';
	   state_we <= '0';
	   end if;
	when s7 =>
	   if R_timer /= "001" then
	   m <= '1';
	   end if;
	   if R_timer /= "010" then
	   state_we <= '0';
	   end if;
	   new_state <= s0;
	   
	   
	when others =>
	   new_state <= s0;
	   state_we <= '0';	    
	end case;
    end process;
end mealy;
