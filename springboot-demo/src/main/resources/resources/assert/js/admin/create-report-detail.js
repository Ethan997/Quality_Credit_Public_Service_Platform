
form_select_listener_cb('create_report_detail_select_filter' , function form_select_listener_fun(data) {

    if(data.value !== 'NULL'){
        file_upload_cb({
            upload_dom_id: 'upload_report_href',
            upload_input_dom_name: 'report_href',
            upload_url: '/file/upload',
            exts: 'pdf',
            folder: 'report/' + data.value + '/',
            cb_fun: function cb_fun() {
                delete_tab(13);
            }
        });
    }

});


function select_check(){

    log($('#upload_report_href').val());

    if($('#create_report_details').val() === 'NULL'){
        toastr.error('请先选择文件上传类型');
        return false;
    }

}

