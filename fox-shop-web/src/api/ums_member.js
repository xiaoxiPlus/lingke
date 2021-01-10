import request from '../utils/request';

export function queryMemberOptions(defaultStatus) {
    return request({
        url: '/wow-api/ums_member/level/'+defaultStatus,
        method: 'get',
    });
};

