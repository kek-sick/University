#!/usr/bin/env python3
import math


def my_abs(val):
    return abs(val)

data = [4, -30, 100, -100, 123, 1, 0, -1, -4]
print(sorted(data, key=my_abs))

# Реализация задания 3