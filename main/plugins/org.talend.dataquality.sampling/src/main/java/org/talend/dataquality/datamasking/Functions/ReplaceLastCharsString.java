// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.datamasking.Functions;

/**
 * created by jgonzalez on 22 juin 2015. See ReplaceLastChars.
 *
 */
public class ReplaceLastCharsString extends ReplaceLastChars<String> {

    @Override
    public String generateMaskedRow(String str) {
        if ((str == null || EMPTY_STRING.equals(str)) && keepNull) {
            return str;
        } else {
            if (str != null && !EMPTY_STRING.equals(str) && integerParam > 0) {
                if (integerParam > str.length()) {
                    integerParam = str.length();
                }
                StringBuilder sb = new StringBuilder(str);
                StringBuilder repla = new StringBuilder(EMPTY_STRING);
                for (int i = sb.length() - integerParam; i < sb.length(); ++i) {
                    if (Character.isDigit(str.charAt(i))) {
                        repla.append(rnd.nextInt(9));
                    } else if (Character.isUpperCase(str.charAt(i))) {
                        repla.append(UPPER.charAt(rnd.nextInt(26)));
                    } else if (Character.isLowerCase(str.charAt(i))) {
                        repla.append(LOWER.charAt(rnd.nextInt(26)));
                    } else {
                        repla.append(str.charAt(i));
                    }
                }
                sb.replace(str.length() - integerParam, str.length(), repla.toString());
                return sb.toString();
            } else {
                return EMPTY_STRING;
            }
        }
    }
}
