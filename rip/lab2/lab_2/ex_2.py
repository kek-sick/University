#!/usr/bin/env python3
from librip.gens import gen_random
from librip.iterators import Unique

data1 = [1, 1, 1, 1, 1, 2, 2, 2, 2, 2]
data2 = gen_random(1, 3, 10)
data3 = ['a','a','b','B','b','B','c']

nique = Unique(data3,ignore_case = False).get_mass_iter()

print(nique)
for i in iter(Unique(data3,ignore_case = False)):
    print(i)


#print(unique)
#print(next(unique))
#print(next(unique))
#print(next(unique))
#print(next(unique))
# Реализация задания 2