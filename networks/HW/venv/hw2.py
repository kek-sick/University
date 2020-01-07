def ErrVect1():
    for i in range(7):
        yield 1 << i


def ErrVect2():
    maxAdd = 2
    Add = 1
    num = 3
    while(num < 96):
        yield num
        num = num + maxAdd
        while(Add < maxAdd):
            yield num
            num = num + Add
            Add = Add << 1
        maxAdd = maxAdd + (Add >> 1)
        Add = 1
    yield num


def ErrVect3():
    Add = 1
    maxAdd = 2
    maxAdd3 = 4
    num = 7
    while(num < 112):
        yield num
        num = num + maxAdd3
        while(maxAdd < maxAdd3-1):
            yield num
            num = num + maxAdd
            while(Add < maxAdd):
                yield num
                num = num + Add
                Add = Add << 1
            maxAdd = maxAdd + (Add >> 1)
            Add = 1
        maxAdd3 = maxAdd3 + (maxAdd >> 1)
        maxAdd = 2
    yield num

def Decode(input_vect):
    check_table = {
        1:0,
        2:1,
        3:3,
        4:2,
        5:6,
        6:4,
        7:5
    }
    decoded_vect = input_vect
    generating_vect = 11
    tail = 8
    while input_vect >= 8:
        while tail << 1 < input_vect:
            tail = tail << 1
            generating_vect = generating_vect << 1
        input_vect = input_vect ^ generating_vect
        tail = 8
        generating_vect = 11
    if input_vect != 0:
        decoded_vect = decoded_vect ^ (1 << check_table[input_vect])

    return decoded_vect >> 3



print('┏━┳━━━━┳━━━┳━━━━━┓')
print('┃i┃ Ci ┃Nk ┃ Ck  ┃')
print('┣━╋━━━━╋━━━╋━━━━━┫')
correct = 0
total = 0
for i in ErrVect1():
    total = total + 1
    if Decode(78 ^ i) == 9:
        correct = correct + 1
print('┃1┃ ',total,'┃',correct,'┃',correct/total,'┃')
#print(correct, '/', total)


correct = 0
total = 0
for i in ErrVect2():
    total = total + 1
    if Decode(78 ^ i) == 9:
        correct = correct + 1
print('┃2┃',total,'┃',correct,'┃',correct/total,'┃')


correct = 0
total = 0
for i in ErrVect3():
    total = total + 1
    if Decode(78 ^ i) == 9:
        correct = correct + 1
print('┃3┃',total,'┃',correct,'┃',correct/total,'┃')


correct = 0
total = 0
for i in ErrVect3():
    total = total + 1
    if Decode(78 ^ (i^127)) == 9:
        correct = correct + 1
print('┃4┃',total,'┃',correct,'┃',correct/total,'┃')


correct = 0
total = 0
for i in ErrVect2():
    total = total + 1
    if Decode(78 ^ (i^127)) == 9:
        correct = correct + 1
print('┃5┃',total,'┃',correct,'┃',correct/total,'┃')


correct = 0
total = 0
for i in ErrVect1():
    total = total + 1
    if Decode(78 ^ (i^127)) == 9:
        correct = correct + 1
print('┃6┃ ',total,'┃',correct,'┃',correct/total,'┃')
print('┗━┻━━━━┻━━━┻━━━━━┛')
#print(bin(Decode(14)))
