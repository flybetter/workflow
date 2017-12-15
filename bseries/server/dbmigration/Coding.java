// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Coding.java

package com.calix.bseries.server.dbmigration;


public class Coding
{

    public Coding()
    {
    }

    public static String convertToNewBase(String s)
    {
        s = (new StringBuffer()).append(s).reverse().toString();
        int ai[] = new int[s.length()];
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < s.length(); i++)
        {
            ai[i] = s.charAt(i) - 28;
            StringBuffer stringbuffer1;
            for(stringbuffer1 = (new StringBuffer()).append(ai[i]); stringbuffer1.length() != 2; stringbuffer1.insert(0, "0"));
            stringbuffer.append(stringbuffer1.toString());
        }

        s = stringbuffer.toString();
        return baseConvertor(s);
    }

    private static String baseConvertor(String s)
    {
        StringBuffer stringbuffer = new StringBuffer();
        String as[] = new String[60];
        as[0] = "0";
        as[1] = "1";
        as[2] = "2";
        as[3] = "3";
        as[4] = "4";
        as[5] = "5";
        as[6] = "6";
        as[7] = "7";
        as[8] = "8";
        as[9] = "9";
        as[10] = "a";
        as[11] = "b";
        as[12] = "c";
        as[13] = "d";
        as[14] = "e";
        as[15] = "f";
        as[16] = "g";
        as[17] = "h";
        as[18] = "i";
        as[19] = "j";
        as[20] = "k";
        as[21] = "l";
        as[22] = "m";
        as[23] = "n";
        as[24] = "o";
        as[25] = "p";
        as[26] = "q";
        as[27] = "r";
        as[28] = "s";
        as[29] = "t";
        as[30] = "u";
        as[31] = "v";
        as[32] = "w";
        as[33] = "x";
        as[34] = "y";
        as[35] = "z";
        as[36] = "A";
        as[37] = "B";
        as[38] = "C";
        as[39] = "D";
        as[40] = "E";
        as[41] = "F";
        as[42] = "G";
        as[43] = "H";
        as[43] = "I";
        as[44] = "J";
        as[45] = "K";
        as[46] = "L";
        as[47] = "M";
        as[48] = "N";
        as[49] = "O";
        as[50] = "P";
        as[51] = "Q";
        as[52] = "R";
        as[53] = "S";
        as[54] = "T";
        as[55] = "U";
        as[56] = "V";
        as[57] = "W";
        as[58] = "X";
        as[59] = "Y";
        long l = 0L;
        long l2 = 0L;
        boolean flag = false;
        boolean flag1 = false;
        String s1 = new String(s);
        for(int j = 0; j < s.length(); j++)
        {
            String s2 = s.substring(j);
            StringBuffer stringbuffer1 = new StringBuffer();
            int k = 0;
            int i = 0;
            long l3 = 0L;
            for(; stringbuffer1.length() != 5 && i < s2.length(); i++)
            {
                String s4 = s2.substring(k, i + 1);
                if(s4.equals("0"))
                {
                    stringbuffer1.append("0");
                    k++;
                } else
                {
                    long l1 = Long.parseLong(s4) / 60L;
                    l3 = Long.parseLong(s4) % 60L;
                    StringBuffer stringbuffer2 = new StringBuffer();
                    if(l1 != 0L)
                        stringbuffer2.append(l1).toString();
                    String s5 = stringbuffer2.toString();
                    StringBuffer stringbuffer3 = new StringBuffer();
                    for(int j1 = 0; j1 < s5.length(); j1++)
                        if(s5.length() != j1 + 1 && !s5.substring(j1, j1 + 1).equals("0") && Integer.parseInt(s5.substring(j1, j1 + 2)) < 60)
                        {
                            stringbuffer3.append(as[Integer.parseInt(s5.substring(j1, j1 + 2))]);
                            j1++;
                        } else
                        {
                            stringbuffer3.append(Integer.parseInt(s5.substring(j1, j1 + 1)));
                        }

                    if(stringbuffer3.length() == 5 - stringbuffer1.length() || i == s2.length() - 1)
                        stringbuffer1.append(stringbuffer3.toString());
                }
            }

            stringbuffer1.append(as[(int)l3]);
            stringbuffer.append(stringbuffer1.toString());
            j = (j + i) - 1;
        }

        String s3 = stringbuffer.toString();
        for(int i1 = 0; (i1 = s3.indexOf("000")) != -1;)
            s3 = (new StringBuffer(s3)).replace(i1, i1 + 3, "Z").toString();

        return s3;
    }

    public static String convertFromBase(String s)
        throws Exception
    {
        s = baseDeconvertor(s);
        StringBuffer stringbuffer = (new StringBuffer()).append(s);
        String as[] = new String[stringbuffer.length() / 2];
        int ai[] = new int[stringbuffer.length() / 2];
        StringBuffer stringbuffer1 = new StringBuffer();
        for(int i = 0; i < stringbuffer.length() / 2; i++)
        {
            as[i] = stringbuffer.toString().substring(2 * i, 2 * i + 2);
            ai[i] = Integer.parseInt(as[i]) + 28;
            stringbuffer1.append((char)ai[i]);
        }

        stringbuffer1 = (new StringBuffer()).append(stringbuffer1).reverse();
        return stringbuffer1.toString();
    }

    private static String baseDeconvertor(String s)
        throws Exception
    {
        String as[] = new String[60];
        as[0] = "0";
        as[1] = "1";
        as[2] = "2";
        as[3] = "3";
        as[4] = "4";
        as[5] = "5";
        as[6] = "6";
        as[7] = "7";
        as[8] = "8";
        as[9] = "9";
        as[10] = "a";
        as[11] = "b";
        as[12] = "c";
        as[13] = "d";
        as[14] = "e";
        as[15] = "f";
        as[16] = "g";
        as[17] = "h";
        as[18] = "i";
        as[19] = "j";
        as[20] = "k";
        as[21] = "l";
        as[22] = "m";
        as[23] = "n";
        as[24] = "o";
        as[25] = "p";
        as[26] = "q";
        as[27] = "r";
        as[28] = "s";
        as[29] = "t";
        as[30] = "u";
        as[31] = "v";
        as[32] = "w";
        as[33] = "x";
        as[34] = "y";
        as[35] = "z";
        as[36] = "A";
        as[37] = "B";
        as[38] = "C";
        as[39] = "D";
        as[40] = "E";
        as[41] = "F";
        as[42] = "G";
        as[43] = "H";
        as[43] = "I";
        as[44] = "J";
        as[45] = "K";
        as[46] = "L";
        as[47] = "M";
        as[48] = "N";
        as[49] = "O";
        as[50] = "P";
        as[51] = "Q";
        as[52] = "R";
        as[53] = "S";
        as[54] = "T";
        as[55] = "U";
        as[56] = "V";
        as[57] = "W";
        as[58] = "X";
        as[59] = "Y";
        for(int i = 0; (i = s.indexOf("Z")) != -1;)
        {
            String s1 = s.substring(0, i);
            String s2 = s.substring(i + 1);
            s = s1 + "000" + s2;
        }

        StringBuffer stringbuffer = new StringBuffer();
        int j = 0;
        long l = 0L;
        for(int k = s.length() / 6; j < k; j++)
        {
            String s3 = s.substring(6 * j, 6 * j + 6);
            StringBuffer stringbuffer1 = new StringBuffer();
            boolean flag = false;
            for(int j1 = 0; j1 < 5; j1++)
            {
                boolean flag3 = false;
                for(int l1 = 0; !flag3; l1++)
                    if(s3.substring(j1, j1 + 1).equals(as[l1]))
                    {
                        flag3 = true;
                        stringbuffer1.append(l1);
                        if(l1 == 0)
                        {
                            if(!flag)
                                stringbuffer.append("0");
                        } else
                        {
                            flag = true;
                        }
                    }

            }

            boolean flag4 = false;
            for(int i2 = 0; !flag4; i2++)
                if(s3.substring(5).equals(as[i2]))
                {
                    flag4 = true;
                    l = i2;
                }

            if(stringbuffer1.toString().equals("00000"))
            {
                if(l != 0L)
                {
                    String s5 = (new Long(l)).toString();
                    String s6 = stringbuffer.toString().substring(0, stringbuffer.length() - s5.length());
                    stringbuffer = (new StringBuffer(s6)).append(s5);
                }
            } else
            {
                stringbuffer.append(Long.parseLong(stringbuffer1.toString()) * 60L + l);
            }
        }

        if(s.length() % 6 != 0)
        {
            String s4 = s.substring(6 * j);
            StringBuffer stringbuffer2 = new StringBuffer();
            if(s4.length() > 1)
            {
                int i1 = 0;
                boolean flag2 = false;
                for(i1 = 0; i1 < s4.length() - 1; i1++)
                {
                    boolean flag5 = false;
                    for(int j2 = 0; !flag5; j2++)
                        if(s4.substring(i1, i1 + 1).equals(as[j2]))
                        {
                            flag5 = true;
                            stringbuffer2.append(j2);
                            if(j2 == 0)
                            {
                                if(!flag2)
                                    stringbuffer.append("0");
                            } else
                            {
                                flag2 = true;
                            }
                        }

                }

                boolean flag6 = false;
                for(int k2 = 0; !flag6; k2++)
                    if(s4.substring(i1).equals(as[k2]))
                    {
                        flag6 = true;
                        l = k2;
                    }

                stringbuffer.append(Long.parseLong(stringbuffer2.toString()) * 60L + l);
            } else
            {
                boolean flag1 = false;
                for(int k1 = 0; !flag1; k1++)
                    if(s4.equals(as[k1]))
                    {
                        flag1 = true;
                        l = k1;
                    }

                stringbuffer.append(l);
            }
        }
        return stringbuffer.toString();
    }
}
