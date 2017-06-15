package hr.fer.zemris.java.simplecomp.instr.tests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;

import java.util.List;

public class TestCall {
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
		when(arg0.isNumber()).thenReturn(true);
		
		when(comp.getMemory()).thenReturn(mem);// memory mock
		
		when(comp.getRegisters()).thenReturn(reg);// registers mock
		when(comp.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX))
					.thenReturn(255);
		
		when(reg.getProgramCounter()).thenReturn(0); // PC mock
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void Constructor1(){
		@SuppressWarnings("unchecked")
		List <InstructionArgument> args = Mockito.mock(List.class);
		when(args.size()).thenReturn(3);
		
		@SuppressWarnings("unused")
		InstrCall call = new InstrCall(args);
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
		InstrCall call = new InstrCall(args);
	}
	
	
	
	@Test
	public void testExecute() {
		InstrCall call = new InstrCall(list);
		call.execute(comp);
		
		// was stack pointer decremented
        verify(comp.getRegisters(), times(1)).setRegisterValue(15, 254); 
		
		// count how many times did we get PC
		verify(reg, times(1)).getProgramCounter();
		
		// was address 0 pushed to stack
        verify(comp.getMemory(), times(1)).setLocation(255, 0);
        
        // was PC updated to subroutine
        verify(reg, times(1)).setProgramCounter(10);

		 
	}
}