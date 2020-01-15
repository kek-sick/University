$(function () {
    let csrftoken = $.cookie('csrftoken');

    $(".course").click(function () {
        let action;
        if ($(this).hasClass('list-group-item-success')){
            $(this).removeClass('list-group-item-success')
            action = 0
        }else {
            $(this).addClass('list-group-item-success');
            action = 1
        }
        $.ajax({
            method: 'post',
            url: 'setcourse/',
            dataType: 'json',
            data: {
                data: 'setcourse',
                action: action,
                course: this.id,
                csrfmiddlewaretoken: csrftoken
            },
        })
    });
    
    $.ajax({
        method: 'post',
        url: '',
        dataType: 'json',
        data: {
            data: 'getcourses',
            csrfmiddlewaretoken: csrftoken
        },
    }).complete(function (msg) {
        for (key in msg['responseJSON']){
            let id_selector = '#' + key;
            $(id_selector).addClass('list-group-item-success')
        }
    })
});