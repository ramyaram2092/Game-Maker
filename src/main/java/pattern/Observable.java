package pattern;

public interface Observable 
{
	public void register(Observer observer);
	public void unregister(Observer observer);

	public void notifyObservers();

}
