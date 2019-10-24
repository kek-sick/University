import random


# Генератор вычленения полей из массива словарей
# Пример:
# goods = [
#    {'title': 'Ковер', 'price': 2000, 'color': 'green'},
#    {'title': 'Диван для отдыха', 'price': 5300, 'color': 'black'}
# ]
# field(goods, 'title') должен выдавать 'Ковер', 'Диван для отдыха'
# field(goods, 'title', 'price') должен выдавать {'title': 'Ковер', 'price': 2000}, {'title': 'Диван для отдыха', 'price': 5300}

def field(list, *args):
    assert len(args) > 0

    if len(args) > 1:
        output = []
        for item in range(len(list)):
            d = {}
            for j in args:
                d.update({j: list[item].get(j)})
            output.append(d)
        print(output)
    else:
        output = [list[x][args[0]] for x in range(len(list))]
        print(output)
    return output
    # Необходимо реализовать генератор 


# Генератор списка случайных чисел
# Пример:
# gen_random(1, 3, 5) должен выдать примерно 2, 2, 3, 2, 1
# Hint: реализация занимает 2 строки
def gen_random(begin, end, num_count):
    output = (random.randint(begin, end) for x in range(num_count))
    #print(output)
    return output
    # Необходимо реализовать генератор
