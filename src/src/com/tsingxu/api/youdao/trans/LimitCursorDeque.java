package com.tsingxu.api.youdao.trans;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * <b>定长光标双端队列</b>
 * 
 * <ol>
 * <li>...</li>
 * </ol>
 * 
 * @since 2012-10-18 上午8:37:06
 * @author x00199331
 */
public class LimitCursorDeque<E> implements Queue<E>
{
	private int size;
	private Entry<E> head;
	private Entry<E> foot;
	private int capacity;
	private Entry<E> cursor;
	private int cursorIndex;

	public LimitCursorDeque(int capacity)
	{
		this.capacity = capacity;
		head = foot = cursor = null;
		cursorIndex = size = 0;
	}

	public E stepForward()
	{
		if (isEmpty())
		{
			return null;
		}

		if (cursor.getBack() != null)
		{
			cursor = cursor.getBack();
			cursorIndex--;
		}

		return cursor.getValue();
	}

	public E stepBackward()
	{
		if (isEmpty())
		{
			return null;
		}

		if (cursor.getNext() != null)
		{
			cursor = cursor.getNext();
			cursorIndex++;
		}

		return cursor.getValue();
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public boolean contains(Object o)
	{
		if (isEmpty())
		{
			return false;
		}

		Entry<E> index = head;
		while (index != null)
		{
			if (index.getValue() == o)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new limitQueueIterator();
	}

	private class limitQueueIterator implements Iterator<E>
	{
		private Entry<E> index = head;
		private int removeCount = 0;

		public limitQueueIterator()
		{
		}

		@Override
		public boolean hasNext()
		{
			return index.getNext() != null;
		}

		@Override
		public E next()
		{
			if (index.getNext() == null)
			{
				throw new NoSuchElementException();
			}

			index = index.getNext();
			removeCount = 0;
			return index.getValue();
		}

		@Override
		public void remove()
		{
			if (removeCount > 0)
			{
				throw new IllegalStateException();
			}

			if (index.getBack() != null)
			{
				index.getBack().setNext(index.getNext());
			}
			else
			{
				head = index.getNext();
			}

			index.setBack(null);
		}
	}

	@Override
	public Object[] toArray()
	{
		Object[] array = new Object[size];

		int i = 0;
		for (Entry<E> index = head; index.getNext() != null; index = index.getNext())
		{
			array[i++] = index.getValue();
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] toArray(Object[] a)
	{
		if (a == null)
		{
			a = new Object[size];
		}
		if (a.length < size)
		{
			a = new Object[size];
		}
		int i = 0;
		for (Entry<E> index = head; index.getNext() != null; index = index.getNext())
		{
			a[i++] = index.getValue();
		}

		return a;
	}

	@Override
	public boolean remove(Object o)
	{
		for (Entry<E> index = head; index.getNext() != null; index = index.getNext())
		{
			if (index.getValue() == o)
			{
				if (index.getBack() != null)
				{
					index.getBack().setNext(index.getNext());
					if (index.getNext() == null)
					{
						foot = index.getBack();
					}
				}
				else
				{
					head = index.getNext();
					if (index.getNext() == null)
					{
						foot = head;
					}
				}
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		for (Iterator<?> ite = c.iterator(); ite.hasNext();)
		{
			for (Entry<E> index = head; index.getNext() != null; index = index.getNext())
			{
				if (index.getValue() == null)
				{
					if (ite.next() != null)
					{
						return false;
					}
				}
				else
				{
					if (!index.getValue().equals(ite.next()))
					{
						return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		for (Iterator<? extends E> ite = c.iterator(); ite.hasNext();)
		{
			offer(ite.next());
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		for (Iterator<?> ite = c.iterator(); ite.hasNext();)
		{
			remove(ite.next());
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(Collection<?> c)
	{
		clear();

		for (Iterator<?> ite = c.iterator(); ite.hasNext();)
		{
			try
			{
				offer((E) ite.next());
			}
			catch (ClassCastException e)
			{
			}
		}

		return true;
	}

	@Override
	public void clear()
	{
		size = 0;
		head = null;
	}

	@Override
	public boolean add(E e)
	{
		return offer(e);
	}

	@Override
	public boolean offer(E e)
	{
		if (e == null)
		{
			throw new NullPointerException();
		}

		if (size == 0)
		{
			head = new Entry<E>(e, null, null);
			foot = cursor = head;
		}
		else
		{
			Entry<E> tmp = new Entry<E>(e, head, null);
			head.setBack(tmp);
			head = tmp;
		}

		size++;

		if (overFlow())
		{
			poll();
		}

		return true;
	}

	@Override
	public E remove()
	{
		E value = poll();
		if (value == null)
		{
			throw new NoSuchElementException();
		}

		return value;
	}

	@Override
	public E poll()
	{
		if (isEmpty())
		{
			return null;
		}

		E value = foot.getValue();

		if (foot == cursor)
		{
			resetCursor();
		}

		if (foot.getBack() != null)
		{
			foot = foot.getBack();
		}
		else
		{
			head = foot = null;
		}

		size--;
		return value;
	}

	public void resetCursor()
	{
		cursor = head;
		cursorIndex = 0;
	}

	@Override
	public E element()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		return foot.getValue();
	}

	@Override
	public E peek()
	{
		if (isEmpty())
		{
			return null;
		}
		return foot.getValue();
	}

	private boolean overFlow()
	{
		return size > capacity;
	}

	public int getCursorIndex()
	{
		return cursorIndex;
	}

}
