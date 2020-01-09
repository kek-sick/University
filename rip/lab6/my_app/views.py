from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.views import View
from django.shortcuts import render
from django import forms
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


class Registration(View):
    def get(self, request):
        form = RegistrationForm()
        print(form)
        return render(request, 'registration_form.html', {'form': form})

    def registration(self, request):
        if request.method == 'POST':
            form = RegistrationForm(request.POST)
            if form.is_valid():
               # успех... создание модели пользователя

                return HttpResponseRedirect('/login/')
        else:
            form = RegistrationForm()

        return render(request, 'registration_form.html', {'form': form})


class RegistrationS(View):
    def get(self, request):
        form = RegistrationForm()
        return render(request, 'registration_form_simple.html', {'form': form})

    def registration(self, request):
        if request.method == 'POST':
            form = RegistrationForm(request.POST)
            if form.is_valid():
               # успех... создание модели пользователя

                return HttpResponseRedirect('/login/')
        else:
            form = RegistrationForm()

        return render(request, 'registration_form_simple.html', {'form': form})


class RegistrationForm(forms.Form):
    username = forms.CharField(min_length=5, label='Логин')
    password = forms.CharField(min_length=6, widget=forms.PasswordInput, label='Пароль')
    password2 = forms.CharField(min_length=6, widget=forms.PasswordInput, label='Повторите ввод')


