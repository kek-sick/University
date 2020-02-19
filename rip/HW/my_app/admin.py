from django.contrib import admin
from my_app.models import Course, Participation


class CourseAdmin(admin.ModelAdmin):
    pass


class ParticipationAdmin(admin.ModelAdmin):
    pass


admin.site.register(Course, CourseAdmin)
admin.site.register(Participation, ParticipationAdmin)

