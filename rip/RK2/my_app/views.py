from django.http import HttpResponse
from django.views import View
from django.shortcuts import render
from my_app.models import Detail


class ExampleClassBased(View):
    def get(self, request):
        return HttpResponse('response fom class based view')


class Details(View):
    def get(self, request):
        data = Detail.objects.all()
        return render(request, 'details.html', {
            'data': data
        })
