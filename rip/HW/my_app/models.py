from django.db import models


class User(models.Model):
    first_name = models.CharField(max_length=60)
    last_name = models.CharField(max_length=60)
    login = models.CharField(max_length=50, unique=True)
    password = models.CharField(max_length=100)
    email = models.EmailField(null=True)


class Course(models.Model):
    title = models.CharField(max_length=60)
    description = models.CharField(max_length=255)
    date = models.DateTimeField()


class Participation(models.Model):
    user_id = models.IntegerField()
    course_id = models.IntegerField()
