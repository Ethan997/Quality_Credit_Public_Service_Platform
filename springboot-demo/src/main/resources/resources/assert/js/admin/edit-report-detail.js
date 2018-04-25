
form_select_listener_cb('create_report_detail_select_filter' , function form_select_listener_fun(data) {

    if(data.value !== 'NULL'){
        file_upload_update({
            file_update_dom_id: 'upload_report_href',
            file_update_callback_dom_id: '',
            file_update_obj_id: parent.global.edit_report_detail_id,
            file_update_url: '/report/update',
            file_update_exts: 'pdf',
            file_update_folder: 'report/' + data.value + '/',
            file_update_category: '',
        });
    }

});

function select_check(){

    if($('#create_report_details').val() === 'NULL'){
        toastr.error('请先选择文件上传类型');
        return false;
    }

}

