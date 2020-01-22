package com.gavin.asmdemo.mvvm

import androidx.databinding.BaseObservable

/**
 * BaseObservable 提供了 notifyChange() 和 notifyPropertyChanged() 两个方法，前者会刷新所有的值域，
 * 后者则只更新对应 BR 的 flag，该 BR 的生成通过注释 @Bindable 生成，可以通过 BR notify 特定属性关联的视图
 *
 *  //如果是 public 修饰符，则可以直接在成员变量上方加上 @Bindable 注解
 *  @Bindable
 *  public String name;
 *  //如果是 private 修饰符，则在成员变量的 get 方法上添加 @Bindable 注解
 *  private String details;
 */
class User : BaseObservable() {
    //如果是 public 修饰符，则可以直接在成员变量上方加上 @Bindable 注解
    var firstName: String = ""
        set(value) {
            notifyChange()
            field = value
        }
    var lastName: String = ""
}