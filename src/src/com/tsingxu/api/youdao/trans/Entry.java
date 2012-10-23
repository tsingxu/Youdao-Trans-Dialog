package com.tsingxu.api.youdao.trans;

/**
 * <b>入口</b>
 * 
 * <ol>
 * <li>...</li>
 * </ol>
 * 
 * @since 2012-10-18 上午10:52:16
 * @author xuhuiqing
 */
public class Entry<E>
{
	private E value;
	private Entry<E> next;
	private Entry<E> back;

	public Entry(E value, Entry<E> next, Entry<E> back)
	{
		this.value = value;
		this.next = next;
		this.back = back;
	}

	public E getValue()
	{
		return value;
	}

	public void setValue(E value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return "Entry [value=" + value + ", next=" + next + ", back=" + back + "]";
	}

	public Entry<E> getNext()
	{
		return next;
	}

	public void setNext(Entry<E> next)
	{
		this.next = next;
	}

	public Entry<E> getBack()
	{
		return back;
	}

	public void setBack(Entry<E> back)
	{
		this.back = back;
	}

}