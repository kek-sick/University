"""lab4 URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from my_app.views import function_view
from my_app.views import ExampleClassBased
from my_app.views import ExampleView
from my_app.views import OrderView
from my_app.views import OrdersView

urlpatterns = [
    path('admin/', admin.site.urls),
    path(r'function_view/', function_view),
    path(r'class_based_view/', ExampleClassBased.as_view()),
    path(r'example_view/', ExampleView.as_view()),
    path(r'order/(?P<id>\d+)', OrderView.as_view(), name='order_url'),
    path(r'orders/', OrdersView.as_view())
]

