form_init_id({init_url: '/complaints' , id:parent.global.edit_complaints_detail_id  ,init_data: edit_complaints_detail_init_data});
form_submit({form_submit_filter: 'edit_complaints_detail_button_filter' , init_obj_fun: edit_complaints_detail_init_obj_fun , submit_url: '/complaints', finish_fun: edit_complaints_detail_finish_fun , is_commit:false});
function edit_complaints_detail_init_obj_fun(fields){
    return {
        id: parent.global.edit_complaints_detail_id,
        name: fields.name,
        time: fields.time,
        mobile: fields.mobile,
        status: fields.status,
        content: fields.content,
        result: fields.result,
    };
}
function edit_complaints_detail_finish_fun(){
    form_clear('name,time,mobile,content,status');
    $("textarea[name='content']").val('');
    $("textarea[name='result']").val('');
}
function edit_complaints_detail_init_data(data){
    $("input[name='name']").attr('value' , data.name);
    $("input[name='time']").attr('value' , data.time);
    $("input[name='mobile']").attr('value' , data.mobile);
    if(data.status === 'INIT'){
        $("input[name='status']").attr('value' , '未处理');
    }else{
        $("input[name='status']").attr('value' , '已处理');
    }
    $("textarea[name='content']").text(data.content);
    $("input[name='result']").attr('value' , data.result);
}