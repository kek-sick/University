from django.http import HttpResponse
from django.views import View
from django.shortcuts import render
from my_app.models import Course


class ExampleClassBased(View):
    def get(self, request):
        return HttpResponse('response fom class based view')


class Courses(View):
    def get(self, request):
        return render(request, 'courses.html', {
            'my_var': 'i came from views.py',
            'list': Course.objects.all(),
        })

