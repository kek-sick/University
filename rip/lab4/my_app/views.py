from django.http import HttpResponse
from django.views import View
from django.shortcuts import render


def function_view(request):
    return HttpResponse('response from function view')


class ExampleClassBased(View):
    def get(self, request):
        return HttpResponse('response fom class based view')


class ExampleView(View):
    def get(self, request):
        return render(request, 'main.html', {
            'my_var': 'i came from views.py',
            'list': [
                'one',
                'two',
                'five',
                'many...'
            ]
        })


class OrdersView(View):
    def get(self, request):
        data = {
            'orders': [
                {'title': 'Первый заказ', 'id': 1, 'element': 'element 1'},
                {'title': 'Второй заказ', 'id': 2, 'element': 'element 2'},
                {'title': 'Третий заказ', 'id': 3, 'element': 'element 3'}
            ]
        }
        return render(request, 'orders.html', data)


class OrderView(View):
    def get(self, request, id):
        data = {
            'order': {
                'id': id
            }
        }
        return render(request, 'order.html', data)
