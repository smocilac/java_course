package hr.fer.zemris.java.simplecomp.instr.tests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;

import java.util.List;

public class TestMove {
	Computer comp;
	Registers reg;
	Memory mem;
	InstructionArgument arg0;
	InstructionArgument arg1;

	@Mock
	List <InstructionArgument> list;
	
	@SuppressWarnings("unchecked")
	@Before
	public void initialize(){
		comp = mock(Computer.class);
		reg = mock(Registers.class);
		mem = mock(Memory.class);
		
		this.list =  (List<InstructionArgument>) mock(List.class);
		arg0 = mock(InstructionArgument.class);
		arg1 = mock(InstructionArgument.class);
		
		when (list.get(0)).thenReturn(arg0); // arguments list mock
		when (list.get(1)).thenReturn(arg1); // arguments list mock
		when (list.size()).thenReturn(2);
		
		when(arg0.getValue()).thenReturn(10); //argument mock
		when(arg0.isRegister()).thenReturn(true);
		
		when(arg1.getValue()).thenReturn(160); //argument mock
		when(arg1.isNumber()).thenReturn(true);
		
		when(comp.getMemory()).thenReturn(mem);// memory mock
		when(comp.getRegisters()).thenReturn(reg);// registers mock
		
		when(mem.getLocation(160)).thenReturn(150);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Constructor1(){
		@SuppressWarnings("unchecked")
		List <InstructionArgument> args = Mockito.mock(List.class);
		when(args.size()).thenReturn(3);
		
		@SuppressWarnings("unused")
		InstrMove test = new InstrMove(args);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void Constructor2(){
		@SuppressWarnings("unchecked")
		List <InstructionArgument> args = Mockito.mock(List.class);
		InstructionArgument a = Mockito.mock(InstructionArgument.class);
		when (a.isRegister()).thenReturn(false);
		
		when(args.size()).thenReturn(2);
		when(args.get(0)).thenReturn(a);
		when(args.get(1)).thenReturn(a);
		
		@SuppressWarnings("unused")
		InstrMove test = new InstrMove(args);
	}
	
	
	
	@Test // move r10, 160
	public void testExecute1() {
		InstrMove test = new InstrMove(list);
		test.execute(comp);
		
		// count how many times did we get PC
		verify(reg, times(0)).getProgramCounter();
		
		// set register
        verify(reg, times(1)).setRegisterValue(10, 160);
	}
	
	@Test // move r10, r3
	public void testExecute2() {
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(5);
		when(reg.getRegisterValue(5)).thenReturn(20);
		
		InstrMove test = new InstrMove(list);
		test.execute(comp);
		
		// count how many times did we get PC
		verify(reg, times(0)).getProgramCounter();
		
		// set register
        verify(reg, times(1)).setRegisterValue(10, 20);
	}
	
	
	@Test // move [r10+12], r3
	public void testExecute3() {	
		when(arg0.isRegister()).thenReturn(true);
		when(arg0.isNumber()).thenReturn(false);
		when(arg0.getValue()).thenReturn(0x01000c0a); // index 10 + offset 22
		when(reg.getRegisterValue(10)).thenReturn(20);
		
		when(arg1.isRegister()).thenReturn(true); // argument two is just a register 3
		when(arg1.isNumber()).thenReturn(false);
		when(arg1.getValue()).thenReturn(3);
		
		when(reg.getRegisterValue(3)).thenReturn(555);
		when(mem.getLocation(22)).thenReturn(11);
		
		
		InstrMove test = new InstrMove(list); // construct move instruction and run it
		test.execute(comp);
		
		// count how many times did we get PC
		verify(reg, times(0)).getProgramCounter();
		
		// set memory location 22 to value of register 3 which is 555
        verify(mem, times(1)).setLocation(32, 555);
        
        
	}
}