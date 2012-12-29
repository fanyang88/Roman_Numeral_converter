package org.test;

public enum KeypadButton {
	 BACKSPACE("<-",KeypadButtonCategory.CLEAR)
	, CE("CE",KeypadButtonCategory.CLEAR)
	, SwitchMode("Switch",KeypadButtonCategory.CLEAR)
	, ZERO("0",KeypadButtonCategory.NUMBER)
	, ONE("1",KeypadButtonCategory.NUMBER)
	, TWO("2",KeypadButtonCategory.NUMBER)
	, THREE("3",KeypadButtonCategory.NUMBER)
	, FOUR("4",KeypadButtonCategory.NUMBER)
	, FIVE("5",KeypadButtonCategory.NUMBER)
	, SIX("6",KeypadButtonCategory.NUMBER)
	, SEVEN("7",KeypadButtonCategory.NUMBER)
	, EIGHT("8",KeypadButtonCategory.NUMBER)
	, NINE("9",KeypadButtonCategory.NUMBER)
	, DECIMAL_SEP(".",KeypadButtonCategory.NUMBER)
	, Melem("M",KeypadButtonCategory.NUMBER)
	, Delem("D",KeypadButtonCategory.NUMBER)
	, Celem("C",KeypadButtonCategory.NUMBER)
	, Lelem("L",KeypadButtonCategory.NUMBER)
	, Xelem("X",KeypadButtonCategory.NUMBER)
	, Velem("V",KeypadButtonCategory.NUMBER)
	, Ielem("I",KeypadButtonCategory.NUMBER)
	, PLUS(" + ",KeypadButtonCategory.OPERATOR)
	, MINUS(" - ",KeypadButtonCategory.OPERATOR)
	, MULTIPLY(" * ",KeypadButtonCategory.OPERATOR)
	, DIV(" / ",KeypadButtonCategory.OPERATOR)
	, CALCULATE("=",KeypadButtonCategory.OPERATOR)
	, ConvertoDecimal("Decimal",KeypadButtonCategory.OPERATOR)
	, ConvertoRN("RN",KeypadButtonCategory.OPERATOR)
	, Principal("principal",KeypadButtonCategory.MEMORYBUFFER)
	, Anualinterest("irate/y",KeypadButtonCategory.MEMORYBUFFER)
	, Timesperyear("times/y",KeypadButtonCategory.MEMORYBUFFER)
	, Years("years",KeypadButtonCategory.MEMORYBUFFER)
	, ComInterest("Interest",KeypadButtonCategory.MEMORYBUFFER)
	, DUMMY("",KeypadButtonCategory.DUMMY);

	CharSequence mText; // Display Text
	KeypadButtonCategory mCategory;
	
	KeypadButton(CharSequence text,KeypadButtonCategory category) {
		mText = text;
		mCategory = category;
	}

	public CharSequence getText() {
		return mText;
	}
}
