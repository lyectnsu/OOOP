package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Stack;

import org.junit.jupiter.api.Test;

import exceptions.ParserException;
import exceptions.WorkerException;
import utility.CommandParser;
import workers.*;

class WorkersTest {

	@Test
	void testFactoryAndWorkers() throws WorkerException, ParserException {
		CommandParser commandParser = new CommandParser();
		
		Worker nullWorker = WorkerFactory.createWorker(commandParser.parseCommand("#"));
		Worker popWorker = WorkerFactory.createWorker(commandParser.parseCommand("POP"));
		Worker pushWorker1 = WorkerFactory.createWorker(commandParser.parseCommand("PUSH a"));
		Worker pushWorker2 = WorkerFactory.createWorker(commandParser.parseCommand("PUSH 2"));
		Worker plusWorker = WorkerFactory.createWorker(commandParser.parseCommand("+"));
		Worker minusWorker = WorkerFactory.createWorker(commandParser.parseCommand("-"));
		Worker multiplyWorker = WorkerFactory.createWorker(commandParser.parseCommand("*"));
		Worker divisionWorker = WorkerFactory.createWorker(commandParser.parseCommand("/"));
		Worker sqrtWorker = WorkerFactory.createWorker(commandParser.parseCommand("SQRT"));
		Worker printWorker = WorkerFactory.createWorker(commandParser.parseCommand("PRINT"));
		Worker defineWorker = WorkerFactory.createWorker(commandParser.parseCommand("DEFINE a 1"));
		
		assertTrue(nullWorker instanceof NullWorker);
		assertTrue(popWorker instanceof PopWorker);
		assertTrue(pushWorker1 instanceof PushWorker);
		assertTrue(pushWorker2 instanceof PushWorker);
		assertTrue(plusWorker instanceof PlusWorker);
		assertTrue(minusWorker instanceof MinusWorker);
		assertTrue(multiplyWorker instanceof MultiplyWorker);
		assertTrue(divisionWorker instanceof DivisionWorker);
		assertTrue(sqrtWorker instanceof SqrtWorker);
		assertTrue(printWorker instanceof PrintWorker);
		assertTrue(defineWorker instanceof DefineWorker);
		
		Stack<Double> stack = new Stack<Double>();
		HashMap<String, Double> varBase = new HashMap<String, Double>();
		
		nullWorker.execute(stack, varBase);
		assertTrue(varBase.isEmpty());
		assertTrue(stack.isEmpty());
		
		defineWorker.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.isEmpty());
		
		nullWorker.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.isEmpty());
		
		pushWorker1.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.contains(1.0));
		assertEquals(stack.size(), 1);
		
		pushWorker2.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.contains(1.0));
		assertTrue(stack.contains(2.0));
		assertEquals(stack.size(), 2);
		
		printWorker.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.contains(1.0));
		assertTrue(stack.contains(2.0));
		assertEquals(stack.size(), 2);
		
		popWorker.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.contains(1.0));
		assertEquals(stack.size(), 1);
		
		stack.clear();
		stack.push(5.0);
		stack.push(15.0);
		plusWorker.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.contains(20.0));
		assertEquals(stack.size(), 1);
		
		stack.clear();
		stack.push(5.0);
		stack.push(15.0);
		minusWorker.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.contains(10.0));
		assertEquals(stack.size(), 1);
		
		stack.clear();
		stack.push(5.0);
		stack.push(15.0);
		multiplyWorker.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.contains(75.0));
		assertEquals(stack.size(), 1);
		
		stack.clear();
		stack.push(5.0);
		stack.push(15.0);
		divisionWorker.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.contains(3.0));
		assertEquals(stack.size(), 1);
		
		stack.clear();
		stack.push(25.0);
		stack.push(64.0);
		sqrtWorker.execute(stack, varBase);
		assertTrue(varBase.containsKey("a"));
		assertEquals(varBase.keySet().size(), 1);
		assertTrue(stack.contains(8.0));
		assertTrue(stack.contains(25.0));
		assertEquals(stack.peek(), 8.0);
		assertEquals(stack.size(), 2);
	}

}
