package hr.fer.zemris.java.simplecomp.instr.tests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;

import java.util.List;

public class TestRet {
	Computer comp;
	Registers reg;
	Memory mem;
	
	@Mock
	List <InstructionArgument> list;
	
	@SuppressWarnings("unchecked")
	@Before
	public void initialize(){
		comp = mock(Computer.class);
		reg = mock(Registers.class);
		mem = mock(Memory.class);
		
		this.list =  (List<InstructionArgument>) mock(List.class);
		
		when (list.size()).thenReturn(0);
		
		when(comp.getMemory()).thenReturn(mem);// memory mock
		when(comp.getMemory().getLocation(255)).thenReturn(10);
		
		when(comp.getRegisters()).thenReturn(reg);// registers mock
		when(comp.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX))
					.thenReturn(254);
		
		when(reg.getProgramCounter()).thenReturn(30); // PC mock
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Constructor1(){
		@SuppressWarnings("unchecked")
		List <InstructionArgument> args = Mockito.mock(List.class);
		when(args.size()).thenReturn(1);
		
		@SuppressWarnings("unused")
		InstrRet ret = new InstrRet(args);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void Constructor2(){
		@SuppressWarnings("unchecked")
		List <InstructionArgument> args = Mockito.mock(List.class);
		InstructionArgument a = Mockito.mock(InstructionArgument.class);
		when (a.isNumber()).thenReturn(false);
		
		when(args.size()).thenReturn(1);
		when(args.get(0)).thenReturn(a);
		
		@SuppressWarnings("unused")
		InstrRet ret = new InstrRet(args);
	}
	
	
	
	@Test
	public void testExecute() {
		InstrRet ret = new InstrRet(list);
		ret.execute(comp);
		
		// was stack pointer incremented
        verify(comp.getRegisters(), times(1)).setRegisterValue(15, 255); 
		
        // was PC updated to subroutine
        verify(reg, times(1)).setProgramCounter(10);
	}
}