package up.light;

/**
 * @version 1.0
 */
public enum FolderType {
	ROOT("path.root") {
		@Override
		String defaultValue() {
			return System.getProperty("user.dir") + Setting.SEPARATOR;
		}
	},
	PLATFORM("path.platform") {
		@Override
		String defaultValue() {
			return ROOT.defaultValue() + Setting.getPlatform() + Setting.SEPARATOR;
		}
	},
	CONFIGURATION("path.config") {
		@Override
		String defaultValue() {
			return PLATFORM.defaultValue() + "config" + Setting.SEPARATOR;
		}
	},
	REPOSITORY("path.repo") {
		@Override
		String defaultValue() {
			return PLATFORM.defaultValue() + "repo" + Setting.SEPARATOR;
		}
	},
	DATA("path.data") {
		@Override
		String defaultValue() {
			return PLATFORM.defaultValue() + "data" + Setting.SEPARATOR;
		}
	},
	LOG("path.log") {
		@Override
		String defaultValue() {
			return PLATFORM.defaultValue() + "log" + Setting.SEPARATOR;
		}
	},
	REPORT("path.report") {
		@Override
		String defaultValue() {
			return PLATFORM.defaultValue() + "report" + Setting.SEPARATOR;
		}
	};

	private String keyName;

	private FolderType(String keyName) {
		this.keyName = keyName;
	}

	String getKeyName() {
		return keyName;
	}

	abstract String defaultValue();
}
