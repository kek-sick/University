from django.db import models


class Manufacturer(models.Model):
    name = models.CharField(max_length=60)


class Detail(models.Model):
    name = models.CharField(max_length=60)
    serial_number = models.IntegerField()
    manufacturer_id = models.ForeignKey(
        Manufacturer,
        on_delete=models.CASCADE
    )
