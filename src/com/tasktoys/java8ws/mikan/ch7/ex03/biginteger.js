/*
 * Copyright(C) 2014-2015 Java 8 Workshop participants. All rights reserved.
 * https://github.com/aosn/java8
 */

// jjs's funny result:
//
// jjs> var b = new java.math.BigInteger('1234567890987654321')
// jjs> b
// 1234567890987654400
// jjs> b.mod(java.math.BigInteger.TEN)
// 1

// Correct program:
var BigInteger = java.math.BigInteger;
var JString = java.lang.String;

var b = new BigInteger('1234567890987654321');
print("[JS Number]")
print(b.longValue()); // 1234567890987654400 (JS Number)
print("[Java long]")
print(JString.valueOf(b.longValue())) // 1234567890987654321 (J long) [OK!]
print("[JS mod]")
print(b.mod(BigInteger.TEN).longValue());
print("[Java mod]")
print(JString.valueOf(b.mod(BigInteger.TEN).longValue()));
