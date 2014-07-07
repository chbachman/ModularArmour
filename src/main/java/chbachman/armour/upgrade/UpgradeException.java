package chbachman.armour.upgrade;

@SuppressWarnings("serial")
public class UpgradeException extends RuntimeException
{
	public UpgradeException ()
	{
	}

	public UpgradeException (String message)
	{
		super (message);
	}

	public UpgradeException (Throwable cause)
	{
		super (cause);
	}

	public UpgradeException (String message, Throwable cause)
	{
		super (message, cause);
	}
}
