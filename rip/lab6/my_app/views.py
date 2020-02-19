from django.http import HttpResponse, HttpResponseRedirect
from django.views import View
from django.shortcuts import render
from django import forms
from my_app.models import Course
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User


class ExampleClassBased(View):
    def get(self, request):
        return HttpResponse('response fom class based view')


class Courses(View):
    def get(self, request):
        user = 'nobody'
        if request.user.is_authenticated:
            user = request.user.username
        else:
            return HttpResponseRedirect('/login/')
        return render(request, 'courses.html', {
            'list': Course.objects.all(),
            'user': user
        })

    def post(self, request):
        if request.POST['logout'] == 'true':
            logout(request)
            return HttpResponseRedirect('/login/')


class Registration(View):
    model = User
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

    def post(self, request):
        errors = []
        if request.method == 'POST':
            form = RegistrationForm(request.POST)
            if form.data['password'] != form.data['password2']:
                form.add_error('password', 'пароли не совпадают')
            elif User.objects.filter(username=form.data['username']).count() != 0:
                form.add_error('username', 'Пользоватеь с таким Логином уже существует')
            elif User.objects.filter(email=form.data['email']).count() != 0:
                form.add_error('email', 'Пользоватеь с таким почтовым адресом уже существует')
            elif form.is_valid():
                # успех... создание модели пользователя
                new_user = User.objects.create_user(form.data['username'], form.data['email'], form.data['password'])
                new_user.first_name = form.data['first_name']
                new_user.last_name = form.data['last_name']
                new_user.save()
                return HttpResponseRedirect('/login/')
        else:
            form = RegistrationForm()
        return render(request, 'registration_form_simple.html', {'form': form})


class RegistrationForm(forms.Form):
    username = forms.CharField(min_length=6, label='Логин')
    first_name = forms.CharField(label='Имя', min_length=1)
    last_name = forms.CharField(label='Фамилия', min_length=1)
    email = forms.EmailField(label='Email')
    password = forms.CharField(min_length=8, widget=forms.PasswordInput, label='Пароль')
    password2 = forms.CharField(min_length=8, widget=forms.PasswordInput, label='Повторите ввод')


class Login(View):
    def get(self, request):
        form = LoginForm()
        return render(request, 'login_form.html', {'form': form})

    def post(self, request):
        if request.method == 'POST':
            form = LoginForm(request.POST)
            form_login = form.data['username']
            form_password = form.data['password']
            user = authenticate(username=form_login, password=form_password)
            if user:
                login(request, user)
                return HttpResponseRedirect('/courses/')
            else:
                form.add_error('username', 'Неверный Логин или Пароль')
        else:
            form = LoginForm()
        return render(request, 'login_form.html', {'form': form})


class LoginForm(forms.Form):
    username = forms.CharField(min_length=6, label='Логин')
    password = forms.CharField(min_length=8, widget=forms.PasswordInput, label='Пароль')
