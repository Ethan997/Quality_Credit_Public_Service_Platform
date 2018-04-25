form_submit({
    form_submit_filter: 'create_rank_detail_button_filter',
    init_obj_fun: function create_rank_detail_init_obj_fun(fields) {
        return {
            name: fields.name,
            type: fields.create_rank_detail_select,
        };
    },
    submit_url: '/rank',
    finish_fun: function create_rank_detail_finish_fun() {
        form_clear('name');
    },
    is_commit: true
});

