
date_render({laydate_dom_id: 'registration_time', laydate_type: 'datetime'});

file_upload({
    upload_dom_id: 'upload_srrUrl',
    upload_input_dom_name: 'srrUrl',
    upload_url: '/file/upload',
    exts: 'jpg|png|bmp',
    folder: 'enterprise/'
});

file_upload({
    upload_dom_id: 'upload_qcfUrl',
    upload_input_dom_name: 'qcfUrl',
    upload_url: '/file/upload',
    exts: 'jpg|png|bmp',
    folder: 'enterprise/'
});

file_upload({
    upload_dom_id: 'upload_srpUrl',
    upload_input_dom_name: 'srpUrl',
    upload_url: '/file/upload',
    exts: 'jpg|png|bmp',
    folder: 'enterprise/'
});

form_submit({
    form_submit_filter: 'create_enterprise_detail_filter',
    init_obj_fun: function create_user_detail_init_obj_fun(fields) {
        return {
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
    },
    submit_url: '/enterprise',
    finish_fun: function create_user_detail_finish_fun() {
        form_clear('name,industry,agencyCode,registrationTime,businessScope,administrativeDivision,legalRepresentative,location,srrUrl,qcfUrl,srpUrl');
        delete_tab(4);
    },
    is_commit: true
});

