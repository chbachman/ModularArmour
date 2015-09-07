package chbachman.armour.upgrade;

import chbachman.api.upgrade.IUpgrade;

@SuppressWarnings("serial")
public class UpgradeException extends RuntimeException {
    public IUpgrade cause;

    public UpgradeException() {
    }

    public UpgradeException(String message, Object... objects) {
        super(String.format(message, objects));
    }

    public UpgradeException(String message, IUpgrade cause) {
        this(message);

        this.cause = cause;
    }

    public UpgradeException(Throwable cause) {
        super(cause);
    }

    public UpgradeException(String message, Throwable cause) {
        super(message, cause);
    }
}
