package hr.fer.zemris.java.simplecomp.instr.tests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;

import java.util.List;

public class TestLoad {
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
		InstrLoad test = new InstrLoad(args);
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
		InstrLoad test = new InstrLoad(args);
	}
	
	
	
	@Test
	public void testExecute() {
		InstrLoad test = new InstrLoad(list);
		test.execute(comp);
		
		// get location from argument
        verify(mem, times(1)).getLocation(160); 
		
		// count how many times did we get PC
		verify(reg, times(0)).getProgramCounter();
		
		// set register
        verify(reg, times(1)).setRegisterValue(10, 150);
        		 
	}
}