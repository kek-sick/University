# Итератор для удаления дубликатов
class Unique(object):
    def __init__(self, items, **kwargs):
        self.params = {'ignore_case':False}
        self.params.update(**kwargs)
        self.items = items
        self.output = []
        self.counter = 0
        # Нужно реализовать конструктор
        # В качестве ключевого аргумента, конструктор должен принимать bool-параметр ignore_case,
        # в зависимости от значения которого будут считаться одинаковые строки в разном регистре
        # Например: ignore_case = True, Aбв и АБВ разные строки
        #           ignore_case = False, Aбв и АБВ одинаковые строки, одна из них удалится
        # По-умолчанию ignore_case = False

        if self.params.get('ignore_case'):
            for i in range(len(self.items)):
                if isinstance(self.items[i],str):
                    self.items[i] = self.items[i].lower()
        for i in self.items:
            if len(self.output) == 0:
                self.output.append(i)
            appendFlag = True
            for j in self.output:
                if i == j:
                    appendFlag = False
                    break
            if appendFlag:
                self.output.append(i)
        #print(self.output)

    def __next__(self):
        # Нужно реализовать __next__
        i = self.counter
        self.counter += 1
        if i < len(self.output):
            return self.output[i]
        else:
            raise StopIteration

    def __iter__(self):
        return self

    def get_mass_iter(self):
        return self.output