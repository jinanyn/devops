<template>
    <div style="padding:10px;">
        <el-form :model="myQueryForm" ref="myQueryForm" label-width="100px">
            <el-form-item
                    label="选择日期"
                    prop="viewDate">
                <el-date-picker placeholder="选择日期" v-model="myQueryForm.viewDate"  type="daterange"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期" unlink-panels
                                style="width: 100%;" value-format="yyyyMMdd" format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('myQueryForm')">提q交</el-button>
                <el-button @click="resetForm('myQueryForm')">重置</el-button>
            </el-form-item>
        </el-form>
        <el-table
                :data="tableData"
                height="400"
                border
                style="width: 100%">
            <el-table-column
                    type="index"
                    label="序号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="shenqingh"
                    label="申请号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="examiner"
                    label="审查员"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="comments"
                    label="备注">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    let initViewData = [new Date().Format('yyyyMMdd'),new Date().Format('yyyyMMdd')];
    module.exports = {
        data() {
            return {
                tableData: [],
                myQueryForm: {
                    viewDate:initViewData
                }
            }
        },
        props:['menuid'],
        methods: {
            submitForm(formName) {
                let myParams = {'viewDate': this.myQueryForm.viewDate[0]+"-"+this.myQueryForm.viewDate[1]};
                myParams.menuid=this.menuid;
                this.getDataFromServer(myParams);
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            getDataFromServer(myParams){
                axios
                    .get('/utility/dataMonitor/utilityCommonOne', {
                        params: myParams
                    })
                    .then(response => (this.tableData = response.data))
                    .catch(function (error) { // 请求失败处理
                        console.log(error);
                    });
            }
        },
        mounted() {
            let myParams = {'viewDate': '','menuid':this.menuid};
            this.getDataFromServer(myParams);
            //console.log(new Date().Format('yyyyMMdd'));
        }
    }
</script>