
$(function () {
    let built_btn = $("[class=built_btn]");
    let from_arg = $("[class=arg_from]");
    let to_arg = $("[class=arg_to]");
    let func = $("[class=func]");
    let output = $("[class=graf]");
    let points =[];
    built_btn.click(function () {
        from_val = parseFloat(from_arg.val());
        to_val = parseFloat(to_arg.val());
        func_val = func.val();
        const step = (to_val - from_val) / 100;
        for (let i = from_val; i <= to_val; i = i +step){
            const x = i;
            let y = eval(func_val);
            points.push([x, y])
        }
        let mydata = [{
            data: points
        }];
        $.plot(output,mydata,{});
    });
});