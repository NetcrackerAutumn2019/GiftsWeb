(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory() :
    typeof define === 'function' && define.amd ? define(factory) :
    (global = global || self, global.FullCalendarLocalesAll = factory());
}(this, function () { 'use strict';

    var _m51 = {
        code: "ru",
        week: {
            dow: 1,
            doy: 4 // The week that contains Jan 4th is the first week of the year.
        },
        buttonText: {
            prev: "Пред",
            next: "След",
            today: "Сегодня",
            month: "Месяц",
            week: "Неделя",
            day: "День",
            list: "Повестка дня"
        },
        weekLabel: "Нед",
        allDayText: "Весь день",
        eventLimitText: function (n) {
            return "+ ещё " + n;
        },
        noEventsMessage: "Нет событий для отображения"
    };

    var _rollupPluginMultiEntry_entryPoint = [
    _m0, _m1, _m2, _m3, _m4, _m5, _m6, _m7, _m8, _m9, _m10, _m11, _m12, _m13, _m14, _m15, _m16, _m17, _m18, _m19, _m20, _m21, _m22, _m23, _m24, _m25, _m26, _m27, _m28, _m29, _m30, _m31, _m32, _m33, _m34, _m35, _m36, _m37, _m38, _m39, _m40, _m41, _m42, _m43, _m44, _m45, _m46, _m47, _m48, _m49, _m50, _m51, _m52, _m53, _m54, _m55, _m56, _m57, _m58, _m59, _m60, _m61, _m62, _m63
    ];

    return _rollupPluginMultiEntry_entryPoint;

}));
