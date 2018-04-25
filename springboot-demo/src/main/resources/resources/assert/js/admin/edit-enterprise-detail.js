date_render({laydate_dom_id: 'registration_time', laydate_type: 'datetime'});
form_init_id({
    init_url: '/enterprise',
    id: parent.global.edit_enterprise_detail_id,
    init_data: function edit_enterprise_detail_init_data(data) {
        $("input[name='name']").attr('value', data.name);
        $("input[name='industry']").attr('value', data.industry);
        $("input[name='agencyCode']").attr('value', data.agencyCode);
        $("input[name='registrationTime']").attr('value', data.registrationTime);
        $("input[name='businessScope']").attr('value', data.businessScope);
        $("input[name='administrativeDivision']").attr('value', data.administrativeDivision);
        $("input[name='legalRepresentative']").attr('value', data.legalRepresentative);
        $("input[name='location']").attr('value', data.location);
        $('#srrUrl').val(data.srrUrl);
        $('#qcfUrl').val(data.qcfUrl);
        $('#srpUrl').val(data.srpUrl);
    }
});
form_submit({
    form_submit_filter: 'edit_user_detail_filter', init_obj_fun: function edit_enterprise_detail_init_obj_fun(fields) {
        return {
            id: parent.global.edit_enterprise_detail_id,
            name: fields.name,
            industry: fields.industry,
            agencyCode: fields.agencyCode,
            registrationTime: fields.registrationTime,
            businessScope: fields.businessScope,
            administrativeDivision: fields.administrativeDivision,
            legalRepresentative: fields.legalRepresentative,
            location: fields.location,
            srrUrl: fields.srrUrl,
            qcfUrl: fields.qcfUrl,
            srpUrl: fields.srpUrl
        };
    }, submit_url: '/enterprise', finish_fun: function edit_enterprise_detail_finish_fun() {
        form_clear('name,industry,agencyCode,registrationTime,businessScope,administrativeDivision,legalRepresentative,location,srrUrl,qcfUrl,srpUrl');
        delete_tab(5);
    }, is_commit: false
});
file_upload_update({
    file_update_dom_id: 'upload_srrUrl',
    file_update_callback_dom_id: 'srrUrl',
    file_update_obj_id: parent.global.edit_enterprise_detail_id,
    file_update_url: '/enterprise/update',
    file_update_exts: 'jpg|png|bmp',
    file_update_folder: 'enterprise/',
    file_update_category: 'srrUrl',
});
file_upload_update({
    file_update_dom_id: 'upload_qcfUrl',
    file_update_callback_dom_id: 'qcfUrl',
    file_update_obj_id: parent.global.edit_enterprise_detail_id,
    file_update_url: '/enterprise/update',
    file_update_exts: 'jpg|png|bmp',
    file_update_folder: 'enterprise/',
    file_update_category: 'qcfUrl',
});
file_upload_update({
    file_update_dom_id: 'upload_srpUrl',
    file_update_callback_dom_id: 'srpUrl',
    file_update_obj_id: parent.global.edit_enterprise_detail_id,
    file_update_url: '/enterprise/update',
    file_update_exts: 'jpg|png|bmp',
    file_update_folder: 'enterprise/',
    file_update_category: 'srpUrl',
});

