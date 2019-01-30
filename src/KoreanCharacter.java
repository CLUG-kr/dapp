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
		��("g") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
						return "kk";
					case ��:
						return "k";
					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("n");
			}
		},
		��("kk"),
		��("n") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ��:
					case ��:
						return "l";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("d") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ��:
						return "tt";
					case ��:
					case ��:
						return "t";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("tt"),
		��("r") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
						return "n";
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
						switch (consonantAssimilation) {
							case Progressive:
								return "n";
							default:
								return "l";
						}
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
						return "l";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("m"),
		��("b") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ��:
						return "pp";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("pp"),
		��("s"),
		��("ss"),
		��("") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ��:
						if (type == Type.Compound && currentCharacter.getJungsung().isInducePalatalization()) {
							return "n";
						} else {
							return "g";
						}
					case ��:
						return "g";
					case ��:
						return "kk";
					case ��:
					case ��:
					case ��:
					case ��:
						return "s";
					case ��:
						if (type == Type.Compound && currentCharacter.getJungsung().isInducePalatalization()) {
							return "n";
						} else {
							return defaultPronunciation;
						}
					case ��:
					case ��:
						return "n";
					case ��:
					case ��:
						return "j";
					case ��:
						return currentCharacter.getJungsung().isInducePalatalization() ? "j" : "d";
					case ��:
					case ��:
						if (type == Type.Compound && currentCharacter.getJungsung().isInducePalatalization()) {
							return "l";
						} else {
							return "r";
						}
					case ��:
					case ��:
						return "m";
					case ��:
					case ��:
						return "b";
					case ��:
					case ��:
						return currentCharacter.getJungsung().isInducePalatalization() ? "ch" : "t";
					case ��:
					case ��:
						return "p";
					case ��:
						return "ss";
					case ��:
						return "ch";
					case ��:
						return "k";
					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("ng") && currentCharacterPronunciation.isEmpty();
			}
		},
		��("j") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ��:
						return "ch";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("jj"),
		��("ch"),
		��("k"),
		��("t") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ��:
					case ��:
						return currentCharacter.getJungsung().isInducePalatalization() ? "ch" : "t";
					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("t");
			}
		},
		��("p") {
			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("p");
			}
		},
		��("h") {
			protected String getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ��:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return "";
						}
					case ��:
						return "kk";
					case ��:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return currentCharacter.getJungsung().isInducePalatalization() ? "ch" : "t";
						}
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
						return currentCharacter.getJungsung().isInducePalatalization() ? "ch" : "t";
					case ��:
						return "k";
					case ��:
						return "p";
					case ��:
						return "s";
					case ��:
						return "r";
					case ��:
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
		��("a", false),
		��("ae", false),
		��("ya", true),
		��("yae", true),
		��("eo", false),
		��("e", false),
		��("yeo", true),
		��("ye", true),
		��("o", false),
		��("wa", false),
		��("wae", false),
		��("oe", false),
		��("yo", true),
		��("u", false),
		��("wo", false),
		��("we", false),
		��("wi", false),
		��("yu", true),
		��("eu", false),
		��("ui", false),
		��("i", true);

		private final String defaultPronunciation;
		private final boolean inducePalatalization;

		Jungsung(String defaultPronunciation, boolean inducePalatalization) {
			this.defaultPronunciation = defaultPronunciation;
			this.inducePalatalization = inducePalatalization;
		}

		public String getPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter) {
			boolean insertHyphen = false;

			if (prevCharacter != null && prevCharacter.isKoreanCharacter() && prevCharacter.getJongsung() == Jongsung.NONE && currentCharacter.getChosung() == Chosung.��) {
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
		��("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
						return "";
					case ��:
						if (type == Type.Compound && nextCharacter.jungsung.isInducePalatalization()) {
							return "ng";
						} else {
							return "";
						}
					case ��:
					case ��:
					case ��:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
					case ��:
					case ��:
						return "";
					case ��:
					case ��:
					case ��:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
						return "";
					case ��:
					case ��:
					case ��:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("n") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
						switch (consonantAssimilation) {
							case Regressive:
								return "l";
							default:
								return "n";
						}
					case ��:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("n") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
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
		��("n") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ��.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		��("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
						return "n";
					case ��:
					case ��:
					case ��:
					case ��:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return "";
						}
					case ��:
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
		��("l") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
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
		��("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
					case ��:
					case ��:
						return "l";
					case ��:
					case ��:
					case ��:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("m") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
					case ��:
						return "l";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("l") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
						return "m";
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
						return "p";
					case ��:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("l"),
		��("l"),
		��("l") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
						return "m";
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
						return "p";
					case ��:
					case ��:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("l") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
						return "";
					case ��:
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
		��("m") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("p") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
					case ��:
						return "m";
					case ��:
					case ��:
						return "";
					case ��:
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
		��("p") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
					case ��:
						return "m";
					case ��:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ��.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		��("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ��.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		��("ng"),
		��("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ��.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		��("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ��.getComplexPronunciation(nextCharacter, consonantAssimilation, type);
			}
		},
		��("k") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
						return "";
					case ��:
					case ��:
					case ��:
						return "ng";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
						return "n";
					case ��:
					case ��:
					case ��:
						return "";
					case ��:
						return "l";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("p") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
						return "";
					default:
						return defaultPronunciation;
				}
			}
		},
		��("t") {
			protected String getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
					case ��:
						return "";
					case ��:
					case ��:
						return "n";
					case ��:
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
	 * First character code point in Hangul Syllables in Unicode table ({@code ��}).
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