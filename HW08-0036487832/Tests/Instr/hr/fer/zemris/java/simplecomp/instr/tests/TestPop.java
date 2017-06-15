package hr.fer.zemris.java.simplecomp.instr.tests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;

import java.util.List;

public class TestPop {
	Computer comp;
	Registers reg;
	Memory mem;
	InstructionArgument arg0;
	
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
		
		when (list.get(0)).thenReturn(arg0); // arguments list mock
		when (list.size()).thenReturn(1);
		
		when(arg0.getValue()).thenReturn(10); //argument mock
		when(arg0.isRegister()).thenReturn(true);
		
		when(comp.getMemory()).thenReturn(mem);// memory mock
		
		when(mem.getLocation(255)).thenReturn(13);
		
		when(comp.getRegisters()).thenReturn(reg);// registers mock
		when(comp.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX))
					.thenReturn(254);
		
		when(reg.getProgramCounter()).thenReturn(110); // PC mock
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Constructor1(){
		@SuppressWarnings("unchecked")
		List <InstructionArgument> args = Mockito.mock(List.class);
		when(args.size()).thenReturn(3);
		
		@SuppressWarnings("unused")
		InstrPop test = new InstrPop(args);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void Constructor2(){
		@SuppressWarnings("unchecked")
		List <InstructionArgument> args = Mockito.mock(List.class);
		InstructionArgument a = Mockito.mock(InstructionArgument.class);
		when (a.isRegister()).thenReturn(false);
		
		when(args.size()).thenReturn(1);
		when(args.get(0)).thenReturn(a);
		
		@SuppressWarnings("unused")
		InstrPop test = new InstrPop(args);
	}
	
	
	
	@Test
	public void testExecute() {
		InstrPop test = new InstrPop(list);
		test.execute(comp);
		
		// was stack pointer incremented
        verify(comp.getRegisters(), times(1)).setRegisterValue(15, 255); 
		
		// count how many times did we get PC, should be none
		verify(reg, times(0)).getProgramCounter();
		
		// get from stack pointer + 1 address
		verify(mem, times(1)).getLocation(255);
		
		// write value to register  
		verify(reg, times(1)).setRegisterValue(10, 13);
	}
}