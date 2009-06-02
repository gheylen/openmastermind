package app.mastermind.model.combination;

import gheylenlib.collection.bag.CycleBag;


/**
 * Works with bytes:
 * 		Will never have a board bigger than 256;
 * 		Will never have a code bigger than 256;
 * 		Will never have more different colors in the code than 256.
 * 
 * 	@deprecated Not finished
 */
public abstract class AbstractCombination
{
	protected byte _length;
	protected byte _amountColors;
	protected byte[] _nodes;
	protected boolean _nodesAsSet;
	
	public AbstractCombination(byte... nodes)
	{
		
	}
	
	public byte getLength()
	{
		return this._length;
	}
	
	public static boolean analyzeResults(CycleBag results, byte expectedLength)
	{
		if(results.getSize() != expectedLength)
			return false;
		
		for(byte i = 0; i < results.getSize(); i++)
			if(results.pop() != Result.CORRECT)
				return false;
		
		return true;
	}
}
