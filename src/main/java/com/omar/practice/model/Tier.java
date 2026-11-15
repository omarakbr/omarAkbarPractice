package com.omar.practice.model;

public record Tier(int fromItem, Integer toItem, int percent) {} // toItem == null means "and beyond"
