import java.io.Serializable;

/**
 * A library that handling Hangul characters in syllable units.
 */
public class KoreanCharacter implements Serializable, Comparable<KoreanCharacter> {
	/**
	 * Required for serialization support.
	 *
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = -2081434254504406150L;

	/**
	 * When consonant assimilation is ambiguous how it should occur,
	 * it designates one of progressive assimilation or regressive assimilation.
	 */
	public enum ConsonantAssimilation {
		/**
		 * Set progressive assimilation.
		 */
		Progressive,

		/**
		 * Set regressive assimilation.
		 */
		Regressive
	}

	/**
	 * Options for applying special rules depending on the type of word.
	 */
	public enum Type {
		/**
		 * Noun like a substantives.
		 */
		Substantives,

		/**
		 * Compound words.
		 */
		Compound,

		/**
		 * Addresses, locations.
		 */
		District,

		/**
		 * Person's full name.
		 */
		Name,

		/**
		 * Common words.
		 */
		Typical
	}

	/**
	 * The consonant used as the final syllable of Hangul, which is called "Jongsung".
	 */
	public enum Chosung {
		ぁ("g") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case お:
					case か:
					case が:
					case き:
					case ぎ:
					case く:
					case ぐ:
						return "kk";
					case ぞ:
						return "k";
					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("n");
			}
		},
		あ("kk"),
		い("n") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ぉ:
					case ぐ:
						return "l";
					default:
						return defaultPronunciation;
				}
			}
		},
		ぇ("d") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ぎ:
						return "tt";
					case う:
					case ぞ:
						return "t";
					default:
						return defaultPronunciation;
				}
			}
		},
		え("tt"),
		ぉ("r") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ぁ:
					case あ:
					case ぃ:
					case お:
					case が:
					case く:
					case け:
					case げ:
					case ご:
					case し:
					case せ:
					case そ:
						return "n";
					case い:
					case ぇ:
					case ぅ:
					case う:
					case さ:
					case ざ:
					case じ:
					case ず:
					case ぞ:
						switch (consonantAssimilation) {
							case Progressive:
								return "n";
							default:
								return "l";
						}
					case ぉ:
					case か:
					case き:
					case ぎ:
					case ぐ:
					case ぜ:
						return "l";
					default:
						return defaultPronunciation;
				}
			}
		},
		け("m"),
		げ("b") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ぎ:
						return "pp";
					default:
						return defaultPronunciation;
				}
			}
		},
		こ("pp"),
		さ("s"),
		ざ("ss"),
		し("") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ぁ:
						if (type == Type.Compound && currentCharacter.getJungsung().isInducePalatalization()) {
							return "n";
						} else {
							return "g";
						}
					case お:
						return "g";
					case あ:
						return "kk";
					case ぃ:
					case き:
					case ご:
					case さ:
						return "s";
					case し:
						if (type == Type.Compound && currentCharacter.getJungsung().isInducePalatalization()) {
							return "n";
						} else {
							return defaultPronunciation;
						}
					case い:
					case う:
						return "n";
					case ぅ:
					case じ:
						return "j";
					case ぇ:
						return currentCharacter.getJungsung().isInducePalatalization() ? "j" : "d";
					case ぉ:
					case ぐ:
						if (type == Type.Compound && currentCharacter.getJungsung().isInducePalatalization()) {
							return "l";
						} else {
							return "r";
						}
					case か:
					case け:
						return "m";
					case が:
					case げ:
						return "b";
					case ぎ:
					case ぜ:
						return currentCharacter.getJungsung().isInducePalatalization() ? "ch" : "t";
					case く:
					case そ:
						return "p";
					case ざ:
						return "ss";
					case ず:
						return "ch";
					case せ:
						return "k";
					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("ng") && currentCharacterPronunciation.isEmpty();
			}
		},
		じ("j") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ぞ:
						return "ch";
					default:
						return defaultPronunciation;
				}
			}
		},
		す("jj"),
		ず("ch"),
		せ("k"),
		ぜ("t") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case じ:
					case ず:
						return currentCharacter.getJungsung().isInducePalatalization() ? "ch" : "t";
					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("t");
			}
		},
		そ("p") {
			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("p");
			}
		},
		ぞ("h") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ぁ:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return "";
						}
					case あ:
						return "kk";
					case ぇ:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return currentCharacter.getJungsung().isInducePalatalization() ? "ch" : "t";
						}
					case ぎ:
					case さ:
					case ざ:
					case じ:
					case ず:
					case ぜ:
						return currentCharacter.getJungsung().isInducePalatalization() ? "ch" : "t";
					case お:
						return "k";
					case が:
						return "p";
					case き:
						return "s";
					case ぐ:
						return "r";
					case げ:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return "p";
						}
					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return !currentCharacterPronunciation.isEmpty() && prevCharacterPronunciation.endsWith(String.valueOf(currentCharacterPronunciation.charAt(0)));
			}
		};

		protected final String defaultPronunciation;

		Chosung(String defaultPronunciation) {
			this.defaultPronunciation = defaultPronunciation;
		}

		public String getPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
			if (prevCharacter == null || !prevCharacter.isKoreanCharacter()) {
				return defaultPronunciation;
			} else {
				String complexPronunciation = getComplexPronunciation(prevCharacter, currentCharacter, consonantAssimilation, type);
				return isNeedHyphen(prevCharacter.getRomanizedString(null, currentCharacter, consonantAssimilation, type), complexPronunciation) ? "-" + complexPronunciation : complexPronunciation;
			}
		}

		protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
			return defaultPronunciation;
		}

		protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
			return false;
		}
	}

	/**
	 * The vowel used as the middle syllable of Hangul, which is called "Jungsung".
	 */
	public enum Jungsung {
		た("a", false),
		だ("ae", false),
		ち("ya", true),
		ぢ("yae", true),
		っ("eo", false),
		つ("e", false),
		づ("yeo", true),
		て("ye", true),
		で("o", false),
		と("wa", false),
		ど("wae", false),
		な("oe", false),
		に("yo", true),
		ぬ("u", false),
		ね("wo", false),
		の("we", false),
		は("wi", false),
		ば("yu", true),
		ぱ("eu", false),
		ひ("ui", false),
		び("i", true);

		private final String defaultPronunciation;
		private final boolean inducePalatalization;

		Jungsung(String defaultPronunciation, boolean inducePalatalization) {
			this.defaultPronunciation = defaultPronunciation;
			this.inducePalatalization = inducePalatalization;
		}

		public String getPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter) {
			boolean insertHyphen = false;

			if (prevCharacter != null && prevCharacter.isKoreanCharacter() && prevCharacter.getJongsung() == Jongsung.NONE && currentCharacter.getChosung() == Chosung.し) {
				switch (prevCharacter.getJungsung().defaultPronunciation.charAt(prevCharacter.getJungsung().defaultPronunciation.length() - 1)) {
					case 'a':
						switch (defaultPronunciation.charAt(0)) {
							case 'a':
							case 'e':
								insertHyphen = true;
						}
						break;
					case 'e':
						switch (defaultPronunciation.charAt(0)) {
							case 'a':
							case 'e':
							case 'o':
							case 'u':
								insertHyphen = true;
						}
						break;
				}
			}

			return insertHyphen ? "_" + defaultPronunciation : defaultPronunciation;
		}

		public boolean isInducePalatalization() {
			return inducePalatalization;
		}
	}

	/**
	 * The consonant used as the final syllable of Hangul, which is called "Jongsung".
	 */
	public enum Jongsung {
		NONE(""),
		ぁ("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case あ:
					case せ:
						return "";
					case し:
						if (type == Type.Compound && nextCharacter.jungsung.isInducePalatalization()) {
							return "ng";
						} else {
							return "";
						}
					case い:
					case け:
					case ぉ:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		あ("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case あ:
					case せ:
					case し:
					case ぞ:
						return "";
					case い:
					case け:
					case ぉ:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		ぃ("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case あ:
					case せ:
						return "";
					case い:
					case け:
					case ぉ:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		い("n") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ぉ:
						switch (consonantAssimilation) {
							case Regressive:
								return "l";
							default:
								return "n";
						}
					case し:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		ぅ("n") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ぉ:
						switch (consonantAssimilation) {
							case Regressive:
								return "l";
							default:
								return "n";
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		う("n") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return い.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		ぇ("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case い:
					case け:
						return "n";
					case え:
					case し:
					case ぜ:
					case ぞ:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return "";
						}
					case ぉ:
						switch (consonantAssimilation) {
							case Regressive:
								return "l";
							default:
								return "n";
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		ぉ("l") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case し:
						if (type == Type.Compound && nextCharacter.getJungsung().isInducePalatalization()) {
							return defaultPronunciation;
						} else {
							return "";
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		お("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ぁ:
					case あ:
					case し:
					case ぞ:
						return "l";
					case い:
					case ぉ:
					case け:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		か("m") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ぉ:
					case け:
					case し:
						return "l";
					default:
						return defaultPronunciation;
				}
			}
		},
		が("l") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case い:
					case ぉ:
						return "m";
					case ぇ:
					case え:
					case げ:
					case さ:
					case ざ:
					case じ:
					case す:
					case ず:
					case せ:
					case ぜ:
					case ぞ:
						return "p";
					case こ:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		き("l"),
		ぎ("l"),
		く("l") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case い:
					case ぉ:
						return "m";
					case ぇ:
					case え:
					case げ:
					case さ:
					case ざ:
					case じ:
					case す:
					case ず:
					case せ:
					case ぜ:
					case ぞ:
						return "p";
					case こ:
					case そ:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		ぐ("l") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ぞ:
						return "";
					case し:
						if (type == Type.Compound && nextCharacter.getJungsung().isInducePalatalization()) {
							return defaultPronunciation;
						} else {
							return "";
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		け("m") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case し:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		げ("p") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case い:
					case ぉ:
					case け:
						return "m";
					case こ:
					case し:
						return "";
					case ぞ:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return "";
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		ご("p") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case い:
					case ぉ:
					case け:
						return "m";
					case こ:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		さ("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ぇ.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		ざ("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ぇ.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		し("ng"),
		じ("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ぇ.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		ず("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ぇ.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		せ("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case あ:
					case し:
						return "";
					case い:
					case け:
					case ぉ:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		ぜ("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case い:
					case け:
						return "n";
					case え:
					case し:
					case ぞ:
						return "";
					case ぉ:
						return "l";
					default:
						return defaultPronunciation;
				}
			}
		},
		そ("p") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case こ:
					case し:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		ぞ("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ぁ:
					case あ:
					case ぇ:
					case え:
					case し:
					case じ:
					case す:
					case ず:
					case せ:
					case ぜ:
					case そ:
					case ぞ:
						return "";
					case い:
					case け:
						return "n";
					case ぉ:
						switch (consonantAssimilation) {
							case Regressive:
								return "l";
							default:
								return "n";
						}
					default:
						return defaultPronunciation;
				}
			}
		};

		protected final String defaultPronunciation;

		Jongsung(String defaultPronunciation) {
			this.defaultPronunciation = defaultPronunciation;
		}

		public String getPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
			return (nextCharacter == null || !nextCharacter.isKoreanCharacter()) ? defaultPronunciation : getComplexPronunciation(nextCharacter, consonantAssimilation, type);
		}

		protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
			return defaultPronunciation;
		}
	}

	/**
	 * First character code point in Hangul Syllables in Unicode table ({@code 亜}).
	 */
	public final static int KoreanLowerValue = 0xAC00;

	/**
	 * Last character code point in Hangul Syllables in Unicode table ({@code �R}).
	 */
	public final static int KoreanUpperValue = 0xD7A3;

	/**
	 * The original character from constructor's argument.
	 */
	private final char character;

	/**
	 * Disassembled initial syllable of Hangul.
	 */
	private Chosung chosung;

	/**
	 * Disassembled middle syllable of Hangul.
	 */
	private Jungsung jungsung;

	/**
	 * Disassembled final syllable of Hangul.
	 */
	private Jongsung jongsung;

	/**
	 * Constructor
	 *
	 * @param koreanCharacter
	 * 		the Hangul or other character
	 */
	public KoreanCharacter(char koreanCharacter) {
		character = koreanCharacter;

		if (isKoreanCharacter(character)) {
			int value = character - KoreanLowerValue;
			chosung = Chosung.values()[value / (21 * 28)];
			jungsung = Jungsung.values()[value % (21 * 28) / 28];
			jongsung = Jongsung.values()[value % 28];
		}
	}

	/**
	 * Constructor with Hangul object with each syllables.
	 *
	 * @param chosung
	 * 		the consonant used as the initial syllable of Hangul.
	 * @param jungsung
	 * 		the vowel used as the middle syllable of Hangul.
	 * @param jongsung
	 * 		the consonant used as the final syllable of Hangul.
	 * @throws NullPointerException
	 * 		if any arguments is null.
	 */
	public KoreanCharacter(Chosung chosung, Jungsung jungsung, Jongsung jongsung) {
		if (chosung == null || jungsung == null || jongsung == null) {
			throw new NullPointerException("All parameters must not be null.");
		}

		this.chosung = chosung;
		this.jungsung = jungsung;
		this.jongsung = jongsung;

		this.character = (char) ((chosung.ordinal() * 21 * 28 + jungsung.ordinal() * 28 + jongsung.ordinal()) + KoreanLowerValue);
	}

	/**
	 * Whether or not the character of this object is Hangul.
	 *
	 * @return Whether all syllables exist to complete Hangul character.
	 */
	public boolean isKoreanCharacter() {
		return chosung != null && jungsung != null && jongsung != null;
	}

	/**
	 * @return the initial syllable if object has Hangul character, and null if not.
	 */
	public Chosung getChosung() {
		return chosung;
	}

	/**
	 * @return the middle syllable if object has Hangul character, and null if not.
	 */
	public Jungsung getJungsung() {
		return jungsung;
	}

	/**
	 * @return the final syllable if object has Hangul character, and null if not.
	 */
	public Jongsung getJongsung() {
		return jongsung;
	}

	/**
	 * @return the character that this object has.
	 */
	public char getCharacter() {
		return character;
	}

	/**
	 * @return the romanized string of the character this object has.
	 */
	public String getRomanizedString() {
		return getRomanizedString(null, null, ConsonantAssimilation.Progressive, Type.Typical);
	}

	/**
	 * @param prevCharacter
	 * 		the character preceding this character in the sentence.
	 * @param nextCharacter
	 * 		the character after this character in the sentence.
	 * @param consonantAssimilation
	 * 		the consonant assimilation type.
	 * @param type
	 * 		the type of word
	 * @return the romanized string of the character this object has.
	 */
	public String getRomanizedString(KoreanCharacter prevCharacter, KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
		if (!isKoreanCharacter()) {
			return toString();
		}

		if (type == Type.Name) {
			prevCharacter = null;
			nextCharacter = null;
		}

		return chosung.getPronunciation(prevCharacter, this, consonantAssimilation, type)
				+ jungsung.getPronunciation(prevCharacter, this)
				+ jongsung.getPronunciation(nextCharacter, consonantAssimilation, type);
	}

	/**
	 * To check if character is in the Hangul Syllable of Unicode table.
	 *
	 * @param character
	 * 		the character to check.
	 * @return true if the character is Hangul
	 */
	public static boolean isKoreanCharacter(char character) {
		return (KoreanLowerValue <= character && character <= KoreanUpperValue);
	}

	/**
	 * Compares this object to another in ascending order.
	 *
	 * @param other
	 * 		the other object to compare to.
	 * @return the value of {@link Character#compareTo}.
	 */
	@Override
	public int compareTo(KoreanCharacter other) {
		return Character.compare(character, other.character);
	}

	/**
	 * Compares this object to another to test if they are equal.
	 *
	 * @param other
	 * 		the other object to compare to.
	 * @return true if this object is equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (other == null || getClass() != other.getClass()) {
			return false;
		}

		return character == ((KoreanCharacter) other).character;
	}

	/**
	 * Return the hash code for this character.
	 *
	 * @return the value of {@link Character#hashCode()}
	 */
	@Override
	public int hashCode() {
		return Character.hashCode(character);
	}

	/**
	 * Returns a {@link String} object representing this character's value.
	 *
	 * @return a string representation of this character.
	 */
	@Override
	public String toString() {
		return String.valueOf(character);
	}
}