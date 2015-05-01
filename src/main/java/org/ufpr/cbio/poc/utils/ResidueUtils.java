/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package org.ufpr.cbio.poc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.DoubleStream;

import org.ufpr.cbio.poc.domain.Grid;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.domain.TopologyContact;

/**
 *
 *
 * @author vfontoura
 */
public class ResidueUtils {

    private static int[] FIXED_SOLUTION = new int[] { 1, 0, 1, 2, 1, 2, 2, 3, 3, 0 };

    // private static int[] FIXED_SOLUTION = new int[] { 0, 0, 0, 1, 2, 2, 2, 1,
    // 1, 2 };

    // private static int[] FIXED_SOLUTION = new int[] { 2, 3, 0, 3, 0, 0, 0, 1,
    // 2, 2 };

    public static List<Residue> createDefaultReference20(String chain) {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(2, 2), ResidueType.valueOf(String.valueOf(chain.charAt(0)))));
        residues.add(new Residue(new Point(2, 3), ResidueType.valueOf(String.valueOf(chain.charAt(1)))));
        residues.add(new Residue(new Point(2, 4), ResidueType.valueOf(String.valueOf(chain.charAt(2)))));
        residues.add(new Residue(new Point(3, 4), ResidueType.valueOf(String.valueOf(chain.charAt(3)))));
        residues.add(new Residue(new Point(4, 4), ResidueType.valueOf(String.valueOf(chain.charAt(4)))));
        residues.add(new Residue(new Point(4, 3), ResidueType.valueOf(String.valueOf(chain.charAt(5)))));
        residues.add(new Residue(new Point(5, 3), ResidueType.valueOf(String.valueOf(chain.charAt(6)))));
        residues.add(new Residue(new Point(6, 3), ResidueType.valueOf(String.valueOf(chain.charAt(7)))));
        residues.add(new Residue(new Point(7, 3), ResidueType.valueOf(String.valueOf(chain.charAt(8)))));
        residues.add(new Residue(new Point(7, 4), ResidueType.valueOf(String.valueOf(chain.charAt(9)))));
        residues.add(new Residue(new Point(7, 5), ResidueType.valueOf(String.valueOf(chain.charAt(10)))));
        residues.add(new Residue(new Point(7, 6), ResidueType.valueOf(String.valueOf(chain.charAt(11)))));
        residues.add(new Residue(new Point(7, 7), ResidueType.valueOf(String.valueOf(chain.charAt(12)))));
        residues.add(new Residue(new Point(6, 7), ResidueType.valueOf(String.valueOf(chain.charAt(13)))));
        residues.add(new Residue(new Point(5, 7), ResidueType.valueOf(String.valueOf(chain.charAt(14)))));
        residues.add(new Residue(new Point(5, 6), ResidueType.valueOf(String.valueOf(chain.charAt(15)))));
        residues.add(new Residue(new Point(5, 5), ResidueType.valueOf(String.valueOf(chain.charAt(16)))));
        residues.add(new Residue(new Point(4, 5), ResidueType.valueOf(String.valueOf(chain.charAt(17)))));
        residues.add(new Residue(new Point(4, 6), ResidueType.valueOf(String.valueOf(chain.charAt(18)))));
        residues.add(new Residue(new Point(3, 6), ResidueType.valueOf(String.valueOf(chain.charAt(19)))));

        return residues;
    }

    public static List<Residue> createDefaultReference100(String chain) {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(2, 2), ResidueType.valueOf(String.valueOf(chain.charAt(0)))));
        residues.add(new Residue(new Point(2, 3), ResidueType.valueOf(String.valueOf(chain.charAt(1)))));
        residues.add(new Residue(new Point(2, 4), ResidueType.valueOf(String.valueOf(chain.charAt(2)))));
        residues.add(new Residue(new Point(3, 4), ResidueType.valueOf(String.valueOf(chain.charAt(3)))));
        residues.add(new Residue(new Point(4, 4), ResidueType.valueOf(String.valueOf(chain.charAt(4)))));
        residues.add(new Residue(new Point(4, 3), ResidueType.valueOf(String.valueOf(chain.charAt(5)))));
        residues.add(new Residue(new Point(5, 3), ResidueType.valueOf(String.valueOf(chain.charAt(6)))));
        residues.add(new Residue(new Point(6, 3), ResidueType.valueOf(String.valueOf(chain.charAt(7)))));
        residues.add(new Residue(new Point(7, 3), ResidueType.valueOf(String.valueOf(chain.charAt(8)))));
        residues.add(new Residue(new Point(8, 3), ResidueType.valueOf(String.valueOf(chain.charAt(9)))));
        residues.add(new Residue(new Point(9, 3), ResidueType.valueOf(String.valueOf(chain.charAt(10)))));
        residues.add(new Residue(new Point(9, 4), ResidueType.valueOf(String.valueOf(chain.charAt(11)))));
        residues.add(new Residue(new Point(9, 5), ResidueType.valueOf(String.valueOf(chain.charAt(12)))));
        residues.add(new Residue(new Point(9, 6), ResidueType.valueOf(String.valueOf(chain.charAt(13)))));
        residues.add(new Residue(new Point(10, 6), ResidueType.valueOf(String.valueOf(chain.charAt(14)))));
        residues.add(new Residue(new Point(11, 6), ResidueType.valueOf(String.valueOf(chain.charAt(15)))));
        residues.add(new Residue(new Point(12, 6), ResidueType.valueOf(String.valueOf(chain.charAt(16)))));
        residues.add(new Residue(new Point(13, 6), ResidueType.valueOf(String.valueOf(chain.charAt(17)))));
        residues.add(new Residue(new Point(14, 6), ResidueType.valueOf(String.valueOf(chain.charAt(18)))));
        residues.add(new Residue(new Point(14, 5), ResidueType.valueOf(String.valueOf(chain.charAt(19)))));
        residues.add(new Residue(new Point(15, 5), ResidueType.valueOf(String.valueOf(chain.charAt(20)))));
        residues.add(new Residue(new Point(16, 5), ResidueType.valueOf(String.valueOf(chain.charAt(21)))));
        residues.add(new Residue(new Point(16, 6), ResidueType.valueOf(String.valueOf(chain.charAt(21)))));
        residues.add(new Residue(new Point(16, 7), ResidueType.valueOf(String.valueOf(chain.charAt(22)))));
        residues.add(new Residue(new Point(16, 8), ResidueType.valueOf(String.valueOf(chain.charAt(23)))));
        residues.add(new Residue(new Point(16, 9), ResidueType.valueOf(String.valueOf(chain.charAt(24)))));
        residues.add(new Residue(new Point(16, 10), ResidueType.valueOf(String.valueOf(chain.charAt(25)))));
        residues.add(new Residue(new Point(16, 11), ResidueType.valueOf(String.valueOf(chain.charAt(26)))));
        residues.add(new Residue(new Point(16, 12), ResidueType.valueOf(String.valueOf(chain.charAt(27)))));
        residues.add(new Residue(new Point(15, 12), ResidueType.valueOf(String.valueOf(chain.charAt(28)))));
        residues.add(new Residue(new Point(14, 12), ResidueType.valueOf(String.valueOf(chain.charAt(29)))));
        residues.add(new Residue(new Point(13, 12), ResidueType.valueOf(String.valueOf(chain.charAt(30)))));
        residues.add(new Residue(new Point(12, 12), ResidueType.valueOf(String.valueOf(chain.charAt(31)))));
        residues.add(new Residue(new Point(12, 13), ResidueType.valueOf(String.valueOf(chain.charAt(32)))));
        residues.add(new Residue(new Point(13, 13), ResidueType.valueOf(String.valueOf(chain.charAt(33)))));
        residues.add(new Residue(new Point(14, 13), ResidueType.valueOf(String.valueOf(chain.charAt(34)))));
        residues.add(new Residue(new Point(15, 13), ResidueType.valueOf(String.valueOf(chain.charAt(35)))));
        residues.add(new Residue(new Point(16, 13), ResidueType.valueOf(String.valueOf(chain.charAt(36)))));
        residues.add(new Residue(new Point(17, 13), ResidueType.valueOf(String.valueOf(chain.charAt(37)))));
        residues.add(new Residue(new Point(18, 13), ResidueType.valueOf(String.valueOf(chain.charAt(38)))));
        residues.add(new Residue(new Point(19, 13), ResidueType.valueOf(String.valueOf(chain.charAt(39)))));
        residues.add(new Residue(new Point(19, 14), ResidueType.valueOf(String.valueOf(chain.charAt(40)))));
        residues.add(new Residue(new Point(19, 15), ResidueType.valueOf(String.valueOf(chain.charAt(41)))));
        residues.add(new Residue(new Point(19, 16), ResidueType.valueOf(String.valueOf(chain.charAt(42)))));
        residues.add(new Residue(new Point(19, 17), ResidueType.valueOf(String.valueOf(chain.charAt(43)))));
        residues.add(new Residue(new Point(19, 18), ResidueType.valueOf(String.valueOf(chain.charAt(44)))));
        residues.add(new Residue(new Point(19, 19), ResidueType.valueOf(String.valueOf(chain.charAt(45)))));
        residues.add(new Residue(new Point(18, 19), ResidueType.valueOf(String.valueOf(chain.charAt(46)))));
        residues.add(new Residue(new Point(17, 19), ResidueType.valueOf(String.valueOf(chain.charAt(47)))));
        residues.add(new Residue(new Point(17, 20), ResidueType.valueOf(String.valueOf(chain.charAt(48)))));
        residues.add(new Residue(new Point(18, 20), ResidueType.valueOf(String.valueOf(chain.charAt(49)))));
        residues.add(new Residue(new Point(19, 20), ResidueType.valueOf(String.valueOf(chain.charAt(50)))));
        residues.add(new Residue(new Point(19, 21), ResidueType.valueOf(String.valueOf(chain.charAt(51)))));
        residues.add(new Residue(new Point(20, 21), ResidueType.valueOf(String.valueOf(chain.charAt(52)))));
        residues.add(new Residue(new Point(21, 21), ResidueType.valueOf(String.valueOf(chain.charAt(53)))));
        residues.add(new Residue(new Point(22, 21), ResidueType.valueOf(String.valueOf(chain.charAt(54)))));
        residues.add(new Residue(new Point(23, 21), ResidueType.valueOf(String.valueOf(chain.charAt(55)))));
        residues.add(new Residue(new Point(24, 21), ResidueType.valueOf(String.valueOf(chain.charAt(56)))));
        residues.add(new Residue(new Point(25, 21), ResidueType.valueOf(String.valueOf(chain.charAt(57)))));
        residues.add(new Residue(new Point(25, 22), ResidueType.valueOf(String.valueOf(chain.charAt(58)))));
        residues.add(new Residue(new Point(25, 23), ResidueType.valueOf(String.valueOf(chain.charAt(59)))));
        residues.add(new Residue(new Point(25, 24), ResidueType.valueOf(String.valueOf(chain.charAt(60)))));
        residues.add(new Residue(new Point(25, 25), ResidueType.valueOf(String.valueOf(chain.charAt(61)))));
        residues.add(new Residue(new Point(25, 26), ResidueType.valueOf(String.valueOf(chain.charAt(62)))));
        residues.add(new Residue(new Point(26, 26), ResidueType.valueOf(String.valueOf(chain.charAt(63)))));
        residues.add(new Residue(new Point(27, 26), ResidueType.valueOf(String.valueOf(chain.charAt(64)))));
        residues.add(new Residue(new Point(27, 25), ResidueType.valueOf(String.valueOf(chain.charAt(65)))));
        residues.add(new Residue(new Point(27, 24), ResidueType.valueOf(String.valueOf(chain.charAt(66)))));
        residues.add(new Residue(new Point(27, 23), ResidueType.valueOf(String.valueOf(chain.charAt(67)))));
        residues.add(new Residue(new Point(27, 22), ResidueType.valueOf(String.valueOf(chain.charAt(68)))));
        residues.add(new Residue(new Point(27, 21), ResidueType.valueOf(String.valueOf(chain.charAt(69)))));
        residues.add(new Residue(new Point(28, 21), ResidueType.valueOf(String.valueOf(chain.charAt(70)))));
        residues.add(new Residue(new Point(28, 20), ResidueType.valueOf(String.valueOf(chain.charAt(71)))));
        residues.add(new Residue(new Point(29, 20), ResidueType.valueOf(String.valueOf(chain.charAt(72)))));
        residues.add(new Residue(new Point(29, 19), ResidueType.valueOf(String.valueOf(chain.charAt(73)))));
        residues.add(new Residue(new Point(30, 19), ResidueType.valueOf(String.valueOf(chain.charAt(74)))));
        residues.add(new Residue(new Point(31, 19), ResidueType.valueOf(String.valueOf(chain.charAt(75)))));
        residues.add(new Residue(new Point(32, 19), ResidueType.valueOf(String.valueOf(chain.charAt(76)))));
        residues.add(new Residue(new Point(33, 19), ResidueType.valueOf(String.valueOf(chain.charAt(77)))));
        residues.add(new Residue(new Point(33, 18), ResidueType.valueOf(String.valueOf(chain.charAt(78)))));
        residues.add(new Residue(new Point(33, 17), ResidueType.valueOf(String.valueOf(chain.charAt(79)))));
        residues.add(new Residue(new Point(34, 17), ResidueType.valueOf(String.valueOf(chain.charAt(80)))));
        residues.add(new Residue(new Point(34, 16), ResidueType.valueOf(String.valueOf(chain.charAt(81)))));
        residues.add(new Residue(new Point(34, 15), ResidueType.valueOf(String.valueOf(chain.charAt(82)))));
        residues.add(new Residue(new Point(34, 14), ResidueType.valueOf(String.valueOf(chain.charAt(83)))));
        residues.add(new Residue(new Point(34, 13), ResidueType.valueOf(String.valueOf(chain.charAt(84)))));
        residues.add(new Residue(new Point(34, 12), ResidueType.valueOf(String.valueOf(chain.charAt(85)))));
        residues.add(new Residue(new Point(34, 11), ResidueType.valueOf(String.valueOf(chain.charAt(86)))));
        residues.add(new Residue(new Point(34, 10), ResidueType.valueOf(String.valueOf(chain.charAt(87)))));
        residues.add(new Residue(new Point(33, 10), ResidueType.valueOf(String.valueOf(chain.charAt(88)))));
        residues.add(new Residue(new Point(33, 11), ResidueType.valueOf(String.valueOf(chain.charAt(89)))));
        residues.add(new Residue(new Point(33, 12), ResidueType.valueOf(String.valueOf(chain.charAt(90)))));
        residues.add(new Residue(new Point(33, 13), ResidueType.valueOf(String.valueOf(chain.charAt(91)))));
        residues.add(new Residue(new Point(33, 14), ResidueType.valueOf(String.valueOf(chain.charAt(92)))));
        residues.add(new Residue(new Point(33, 15), ResidueType.valueOf(String.valueOf(chain.charAt(93)))));
        residues.add(new Residue(new Point(33, 16), ResidueType.valueOf(String.valueOf(chain.charAt(94)))));
        residues.add(new Residue(new Point(32, 16), ResidueType.valueOf(String.valueOf(chain.charAt(95)))));
        residues.add(new Residue(new Point(31, 16), ResidueType.valueOf(String.valueOf(chain.charAt(96)))));
        residues.add(new Residue(new Point(30, 16), ResidueType.valueOf(String.valueOf(chain.charAt(97)))));
        residues.add(new Residue(new Point(29, 16), ResidueType.valueOf(String.valueOf(chain.charAt(98)))));

        return residues;
    }

    public static List<Residue> createDefaultReference100_1(String chain) {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(9, 5), ResidueType.valueOf(String.valueOf(chain.charAt(0)))));
        residues.add(new Residue(new Point(9, 6), ResidueType.valueOf(String.valueOf(chain.charAt(1)))));
        residues.add(new Residue(new Point(9, 7), ResidueType.valueOf(String.valueOf(chain.charAt(2)))));
        residues.add(new Residue(new Point(9, 8), ResidueType.valueOf(String.valueOf(chain.charAt(3)))));
        residues.add(new Residue(new Point(9, 9), ResidueType.valueOf(String.valueOf(chain.charAt(4)))));
        residues.add(new Residue(new Point(9, 10), ResidueType.valueOf(String.valueOf(chain.charAt(5)))));
        residues.add(new Residue(new Point(9, 11), ResidueType.valueOf(String.valueOf(chain.charAt(6)))));
        residues.add(new Residue(new Point(9, 12), ResidueType.valueOf(String.valueOf(chain.charAt(7)))));
        residues.add(new Residue(new Point(9, 13), ResidueType.valueOf(String.valueOf(chain.charAt(8)))));
        residues.add(new Residue(new Point(9, 14), ResidueType.valueOf(String.valueOf(chain.charAt(9)))));
        residues.add(new Residue(new Point(8, 14), ResidueType.valueOf(String.valueOf(chain.charAt(10)))));
        residues.add(new Residue(new Point(7, 14), ResidueType.valueOf(String.valueOf(chain.charAt(11)))));
        residues.add(new Residue(new Point(7, 13), ResidueType.valueOf(String.valueOf(chain.charAt(12)))));
        residues.add(new Residue(new Point(7, 12), ResidueType.valueOf(String.valueOf(chain.charAt(13)))));
        residues.add(new Residue(new Point(7, 11), ResidueType.valueOf(String.valueOf(chain.charAt(14)))));
        residues.add(new Residue(new Point(7, 10), ResidueType.valueOf(String.valueOf(chain.charAt(15)))));
        residues.add(new Residue(new Point(6, 10), ResidueType.valueOf(String.valueOf(chain.charAt(16)))));
        residues.add(new Residue(new Point(5, 10), ResidueType.valueOf(String.valueOf(chain.charAt(17)))));
        residues.add(new Residue(new Point(4, 10), ResidueType.valueOf(String.valueOf(chain.charAt(19)))));
        residues.add(new Residue(new Point(3, 10), ResidueType.valueOf(String.valueOf(chain.charAt(18)))));
        residues.add(new Residue(new Point(3, 9), ResidueType.valueOf(String.valueOf(chain.charAt(20)))));
        residues.add(new Residue(new Point(3, 8), ResidueType.valueOf(String.valueOf(chain.charAt(21)))));
        residues.add(new Residue(new Point(4, 8), ResidueType.valueOf(String.valueOf(chain.charAt(22)))));
        residues.add(new Residue(new Point(5, 8), ResidueType.valueOf(String.valueOf(chain.charAt(23)))));
        residues.add(new Residue(new Point(5, 7), ResidueType.valueOf(String.valueOf(chain.charAt(24)))));
        residues.add(new Residue(new Point(6, 7), ResidueType.valueOf(String.valueOf(chain.charAt(25)))));
        residues.add(new Residue(new Point(6, 6), ResidueType.valueOf(String.valueOf(chain.charAt(26)))));
        residues.add(new Residue(new Point(7, 6), ResidueType.valueOf(String.valueOf(chain.charAt(27)))));
        residues.add(new Residue(new Point(7, 5), ResidueType.valueOf(String.valueOf(chain.charAt(28)))));
        residues.add(new Residue(new Point(7, 4), ResidueType.valueOf(String.valueOf(chain.charAt(29)))));
        residues.add(new Residue(new Point(6, 4), ResidueType.valueOf(String.valueOf(chain.charAt(30)))));
        residues.add(new Residue(new Point(5, 4), ResidueType.valueOf(String.valueOf(chain.charAt(31)))));
        residues.add(new Residue(new Point(5, 3), ResidueType.valueOf(String.valueOf(chain.charAt(32)))));
        residues.add(new Residue(new Point(5, 2), ResidueType.valueOf(String.valueOf(chain.charAt(33)))));
        residues.add(new Residue(new Point(6, 2), ResidueType.valueOf(String.valueOf(chain.charAt(34)))));
        residues.add(new Residue(new Point(7, 2), ResidueType.valueOf(String.valueOf(chain.charAt(35)))));
        residues.add(new Residue(new Point(8, 2), ResidueType.valueOf(String.valueOf(chain.charAt(36)))));
        residues.add(new Residue(new Point(9, 2), ResidueType.valueOf(String.valueOf(chain.charAt(37)))));
        residues.add(new Residue(new Point(10, 2), ResidueType.valueOf(String.valueOf(chain.charAt(38)))));
        residues.add(new Residue(new Point(11, 2), ResidueType.valueOf(String.valueOf(chain.charAt(39)))));
        residues.add(new Residue(new Point(11, 3), ResidueType.valueOf(String.valueOf(chain.charAt(40)))));
        residues.add(new Residue(new Point(11, 4), ResidueType.valueOf(String.valueOf(chain.charAt(41)))));
        residues.add(new Residue(new Point(11, 5), ResidueType.valueOf(String.valueOf(chain.charAt(42)))));
        residues.add(new Residue(new Point(11, 6), ResidueType.valueOf(String.valueOf(chain.charAt(43)))));
        residues.add(new Residue(new Point(11, 7), ResidueType.valueOf(String.valueOf(chain.charAt(44)))));
        residues.add(new Residue(new Point(11, 8), ResidueType.valueOf(String.valueOf(chain.charAt(45)))));
        residues.add(new Residue(new Point(11, 9), ResidueType.valueOf(String.valueOf(chain.charAt(46)))));
        residues.add(new Residue(new Point(11, 10), ResidueType.valueOf(String.valueOf(chain.charAt(47)))));
        residues.add(new Residue(new Point(11, 11), ResidueType.valueOf(String.valueOf(chain.charAt(48)))));
        residues.add(new Residue(new Point(11, 12), ResidueType.valueOf(String.valueOf(chain.charAt(49)))));
        residues.add(new Residue(new Point(11, 13), ResidueType.valueOf(String.valueOf(chain.charAt(50)))));
        residues.add(new Residue(new Point(11, 14), ResidueType.valueOf(String.valueOf(chain.charAt(51)))));
        residues.add(new Residue(new Point(11, 15), ResidueType.valueOf(String.valueOf(chain.charAt(52)))));
        residues.add(new Residue(new Point(12, 15), ResidueType.valueOf(String.valueOf(chain.charAt(53)))));
        residues.add(new Residue(new Point(12, 16), ResidueType.valueOf(String.valueOf(chain.charAt(54)))));
        residues.add(new Residue(new Point(12, 17), ResidueType.valueOf(String.valueOf(chain.charAt(55)))));
        residues.add(new Residue(new Point(11, 17), ResidueType.valueOf(String.valueOf(chain.charAt(56)))));
        residues.add(new Residue(new Point(10, 17), ResidueType.valueOf(String.valueOf(chain.charAt(57)))));
        residues.add(new Residue(new Point(9, 17), ResidueType.valueOf(String.valueOf(chain.charAt(58)))));
        residues.add(new Residue(new Point(8, 17), ResidueType.valueOf(String.valueOf(chain.charAt(59)))));
        residues.add(new Residue(new Point(7, 17), ResidueType.valueOf(String.valueOf(chain.charAt(60)))));
        residues.add(new Residue(new Point(6, 17), ResidueType.valueOf(String.valueOf(chain.charAt(61)))));
        residues.add(new Residue(new Point(5, 17), ResidueType.valueOf(String.valueOf(chain.charAt(62)))));
        residues.add(new Residue(new Point(5, 16), ResidueType.valueOf(String.valueOf(chain.charAt(63)))));
        residues.add(new Residue(new Point(6, 16), ResidueType.valueOf(String.valueOf(chain.charAt(64)))));
        residues.add(new Residue(new Point(6, 15), ResidueType.valueOf(String.valueOf(chain.charAt(65)))));
        residues.add(new Residue(new Point(5, 15), ResidueType.valueOf(String.valueOf(chain.charAt(66)))));
        residues.add(new Residue(new Point(4, 15), ResidueType.valueOf(String.valueOf(chain.charAt(67)))));
        residues.add(new Residue(new Point(3, 15), ResidueType.valueOf(String.valueOf(chain.charAt(68)))));
        residues.add(new Residue(new Point(2, 15), ResidueType.valueOf(String.valueOf(chain.charAt(69)))));
        residues.add(new Residue(new Point(2, 16), ResidueType.valueOf(String.valueOf(chain.charAt(70)))));
        residues.add(new Residue(new Point(2, 17), ResidueType.valueOf(String.valueOf(chain.charAt(71)))));
        residues.add(new Residue(new Point(2, 18), ResidueType.valueOf(String.valueOf(chain.charAt(72)))));
        residues.add(new Residue(new Point(3, 18), ResidueType.valueOf(String.valueOf(chain.charAt(73)))));
        residues.add(new Residue(new Point(4, 18), ResidueType.valueOf(String.valueOf(chain.charAt(74)))));
        residues.add(new Residue(new Point(4, 19), ResidueType.valueOf(String.valueOf(chain.charAt(75)))));
        residues.add(new Residue(new Point(5, 19), ResidueType.valueOf(String.valueOf(chain.charAt(76)))));
        residues.add(new Residue(new Point(5, 20), ResidueType.valueOf(String.valueOf(chain.charAt(77)))));
        residues.add(new Residue(new Point(6, 20), ResidueType.valueOf(String.valueOf(chain.charAt(78)))));
        residues.add(new Residue(new Point(7, 20), ResidueType.valueOf(String.valueOf(chain.charAt(79)))));
        residues.add(new Residue(new Point(8, 20), ResidueType.valueOf(String.valueOf(chain.charAt(80)))));
        residues.add(new Residue(new Point(9, 20), ResidueType.valueOf(String.valueOf(chain.charAt(81)))));
        residues.add(new Residue(new Point(10, 20), ResidueType.valueOf(String.valueOf(chain.charAt(82)))));
        residues.add(new Residue(new Point(11, 20), ResidueType.valueOf(String.valueOf(chain.charAt(83)))));
        residues.add(new Residue(new Point(12, 20), ResidueType.valueOf(String.valueOf(chain.charAt(84)))));
        residues.add(new Residue(new Point(13, 20), ResidueType.valueOf(String.valueOf(chain.charAt(85)))));
        residues.add(new Residue(new Point(14, 20), ResidueType.valueOf(String.valueOf(chain.charAt(86)))));
        residues.add(new Residue(new Point(14, 19), ResidueType.valueOf(String.valueOf(chain.charAt(87)))));
        residues.add(new Residue(new Point(14, 18), ResidueType.valueOf(String.valueOf(chain.charAt(88)))));
        residues.add(new Residue(new Point(14, 17), ResidueType.valueOf(String.valueOf(chain.charAt(89)))));
        residues.add(new Residue(new Point(14, 16), ResidueType.valueOf(String.valueOf(chain.charAt(90)))));
        residues.add(new Residue(new Point(14, 15), ResidueType.valueOf(String.valueOf(chain.charAt(91)))));
        residues.add(new Residue(new Point(14, 14), ResidueType.valueOf(String.valueOf(chain.charAt(92)))));
        residues.add(new Residue(new Point(14, 13), ResidueType.valueOf(String.valueOf(chain.charAt(93)))));
        residues.add(new Residue(new Point(14, 12), ResidueType.valueOf(String.valueOf(chain.charAt(94)))));
        residues.add(new Residue(new Point(14, 11), ResidueType.valueOf(String.valueOf(chain.charAt(95)))));
        residues.add(new Residue(new Point(14, 10), ResidueType.valueOf(String.valueOf(chain.charAt(96)))));
        residues.add(new Residue(new Point(14, 9), ResidueType.valueOf(String.valueOf(chain.charAt(97)))));
        residues.add(new Residue(new Point(14, 8), ResidueType.valueOf(String.valueOf(chain.charAt(98)))));
        return residues;

    }

    public static List<Residue> parseChainSequence(String chain) {

        int chainLength = chain.length();
        int x = 0, y = 0, minX = 0, minY = 0;
        List<Residue> residues = new ArrayList<>();
        Residue initialResidue = null;
        initialResidue =
            new Residue(createInitialPoint(chainLength), ResidueType.valueOf(String.valueOf(chain.charAt(0))));

        for (int i = 0; i < chainLength; i++) {
            if (i == 0) {
                residues.add(initialResidue);
                x = initialResidue.getPoint().getX();
                y = initialResidue.getPoint().getY();
                minX = x;
                minY = y;
            } else {
                int solution = FIXED_SOLUTION[i - 1];
                // int solution = generateSolution[i - 1];
                switch (solution) {
                // 0 - LEFT
                    case 0:
                        y++;
                        break;
                    // 1 - FORWARD
                    case 1:
                        x++;
                        break;
                    // 2 - RIGHT
                    case 2:
                        y--;
                        break;
                    case 3:
                        x--;
                        break;
                    default:
                        // TODO: exception
                        break;
                }
                if (x < minX) {
                    minX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                residues.add(new Residue(new Point(x, y), ResidueType.valueOf(String.valueOf(chain.charAt(i)))));
            }

        }
        for (Residue residue : residues) {
            residue.setPoint(new Residue.Point(residue.getPoint().x - minX, residue.getPoint().y - minY));
        }
        return residues;
    }

    public static Residue.Point createInitialPoint(int max) {

        Random random = new Random();
        return new Residue.Point(random.nextInt(max), random.nextInt(max));

    }

    public static Set<TopologyContact> getTopologyContacts(List<Residue> residues, Grid grid) {

        Set<TopologyContact> topologyContacts = new HashSet<>();
        int[][] matrix = grid.getMatrix();
        int index = 0;
        for (int i = 0; i < residues.size(); i++) {
            if (residues.get(i).getResidueType().equals(ResidueType.P)) {
                continue;
            }
            if (residues.get(i).getPoint().y + 1 < matrix.length) {
                index = matrix[residues.get(i).getPoint().y + 1][residues.get(i).getPoint().x];
                // test up
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }
            if (residues.get(i).getPoint().x + 1 < matrix.length) {
                // test right
                index = matrix[residues.get(i).getPoint().y][residues.get(i).getPoint().x + 1];
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }
            if (residues.get(i).getPoint().y - 1 >= 0) {
                // test down
                index = matrix[residues.get(i).getPoint().y - 1][residues.get(i).getPoint().x];
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }
            if (residues.get(i).getPoint().x - 1 >= 0) {
                // test back
                index = matrix[residues.get(i).getPoint().y][residues.get(i).getPoint().x - 1];
                if (isTopologicalContact(i, index, residues)) {
                    topologyContacts.add(new TopologyContact(residues.get(i), residues.get(index)));
                }
            }

        }
        return topologyContacts;
    }

    public static boolean isTopologicalContact(int i, int index, List<Residue> residues) {

        if (i != index + 1 && i != index - 1 && index != -1) {

            return residues.get(index).getResidueType().equals(ResidueType.H);
        }
        return false;
    }

    public static List<Residue> translateToOrigin(List<Residue> residues) {

        int minX = residues.get(0).getPoint().getX();
        int minY = residues.get(0).getPoint().getY();
        for (Residue residue : residues) {
            int x = residue.getPoint().getX();
            int y = residue.getPoint().getY();
            if (x < minX) {
                minX = x;
            }
            if (y < minY) {
                minY = y;
            }
        }

        List<Residue> changed = new ArrayList<Residue>();
        for (Residue residue : residues) {
            Point point = residue.getPoint();
            int x = point.getX() - minX + 2;
            int y = point.getY() - minY + 2;
            Point newPoint = new Point(x, y);
            Residue newResidue = new Residue();
            newResidue.setResidueType(residue.getResidueType());
            newResidue.setPoint(newPoint);
            changed.add(newResidue);
        }
        return changed;

    }

    public static EnumMovements[] toMovementsArray(int[] solutions) {

        EnumMovements[] movements = new EnumMovements[solutions.length];
        for (int i = 0; i < solutions.length; i++) {
            switch (solutions[i]) {
                case 0: {
                    if (i == 0 || i == solutions.length - 1) {
                        movements[i] = EnumMovements.ROTATE_90_CLOCKWISE;
                    } else {
                        movements[i] = EnumMovements.CORNER;
                    }
                    break;
                }
                case 1: {
                    if (i == 0 || i == solutions.length - 1) {
                        movements[i] = EnumMovements.ROTATE_180_CLOCKWISE;
                    } else {
                        movements[i] = EnumMovements.CRANKSHAFT;
                    }
                    break;
                }
                default:
                    break;
            }
        }
        return movements;
    }

    public static void main(String[] args) {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(0, 0), ResidueType.H));
        residues.add(new Residue(new Point(1, 0), ResidueType.H));
        residues.add(new Residue(new Point(5, 5), ResidueType.H));

        System.out.println(getAvgDistance(residues));
    }

    /**
     * @param pROTEIN_CHAIN
     * @return
     */
    public static List<Residue> createDefaultReference10(String chain) {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(2, 2), ResidueType.valueOf(String.valueOf(chain.charAt(0)))));
        residues.add(new Residue(new Point(2, 3), ResidueType.valueOf(String.valueOf(chain.charAt(1)))));
        residues.add(new Residue(new Point(2, 4), ResidueType.valueOf(String.valueOf(chain.charAt(2)))));
        residues.add(new Residue(new Point(3, 4), ResidueType.valueOf(String.valueOf(chain.charAt(3)))));
        residues.add(new Residue(new Point(4, 4), ResidueType.valueOf(String.valueOf(chain.charAt(4)))));
        residues.add(new Residue(new Point(4, 3), ResidueType.valueOf(String.valueOf(chain.charAt(5)))));
        residues.add(new Residue(new Point(5, 3), ResidueType.valueOf(String.valueOf(chain.charAt(6)))));
        residues.add(new Residue(new Point(6, 3), ResidueType.valueOf(String.valueOf(chain.charAt(7)))));
        residues.add(new Residue(new Point(7, 3), ResidueType.valueOf(String.valueOf(chain.charAt(8)))));
        residues.add(new Residue(new Point(7, 4), ResidueType.valueOf(String.valueOf(chain.charAt(9)))));
        return residues;
    }

    public static List<Residue> cloneResidueList(List<Residue> residues) {

        List<Residue> clone = new ArrayList<Residue>();
        for (Residue residue : residues) {
            clone.add((Residue) residue.clone());
        }
        return clone;
    }

    public static int getCollisionsCount(List<Residue> residues) {

        Set<Point> pointsSet = new HashSet<>();
        int count = 0;
        for (int i = 0; i < residues.size(); i++) {
            boolean added = pointsSet.add(residues.get(i).getPoint());
            if (!added) {
                count++;
            }
        }
        return count;
    }

    public static double getMaxPointsDistance(List<Residue> residues) {

        double maxDistance = 0;
        for (int i = 0; i < residues.size(); i++) {
            Residue residue1 = residues.get(i);
            Point point1 = residue1.getPoint();
            for (int j = 0; j < residues.size(); j++) {
                Residue residue2 = residues.get(j);
                Point point2 = residue2.getPoint();
                double distance = calculatePointsDistance(point1, point2);
                if (maxDistance < distance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }

    public static double calculatePointsDistance(Point point1, Point point2) {

        int x1 = point1.getX();
        int y1 = point1.getY();

        int x2 = point2.getX();
        int y2 = point2.getY();

        int deltax = x2 - x1;
        int deltay = y2 - y1;

        double powX = Math.pow(deltax, 2);
        double powY = Math.pow(deltay, 2);

        return Math.sqrt(powX + powY);

    }

    public static double getAvgDistance(List<Residue> residues) {

        double sumOfDistances = residues.stream().flatMapToDouble(r -> {
            double[] distances = new double[residues.size()];
            for (int i = 0; i < residues.size(); i++) {
                Point point1 = r.getPoint();
                Point point2 = residues.get(i).getPoint();
                double distance = calculatePointsDistance(point1, point2);
                if (distance != 0) {
                    distances[i] = distance;
                }
            }
            return DoubleStream.of(distances);
        }

        ).sum();
        return sumOfDistances / (residues.size() * 2);
    }
}
