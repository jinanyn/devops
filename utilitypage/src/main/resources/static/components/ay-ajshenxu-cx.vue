<template>
    <div style="padding:10px;">
        <el-form :inline="true" :model="mySumbitForm" ref="mySumbitForm" label-width="100px"
                 :label-position="labelPosition" size="mini">
            <el-row>
                <el-col :span="16">
                    <el-form-item label="案件所属单元" prop="shenchadydm">
                        <el-input v-model="mySumbitForm.shenchadydm" placeholder="输入单元代码" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="16">
                    <el-form-item label="申请号" prop="shenqingh">
                        <el-input v-model="mySumbitForm.shenqingh" placeholder="输入申请号" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('mySumbitForm')">查询案件顺序</el-button>
                        <el-button @click="resetForm('mySumbitForm')">重置</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="16">
                    <el-form-item label="查询结果" prop="data1">
                        <el-input type="textarea" v-model="mySumbitForm.data1" :rows="3" placeholder="查询结果" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
    module.exports = {
        data() {
            return {
                labelPosition: 'left',
                mySumbitForm: {
                    shenqingh: '',
                    shenchadydm: '',
                    data1:''
                }
            }
        },
        methods: {
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            submitForm(formName) {
                let formData = new FormData();
                formData.append("shenqingh", this.mySumbitForm.shenqingh);
                formData.append("shenchadydm", this.mySumbitForm.shenchadydm);
                axios.post('/utility/ayredisOperate/selectajsx', formData).
                then(response => (
                    this.mySumbitForm.data1==response.data))
                    .catch(function (error) { // 请求失败处理
                        console.log(error);
                    }

                    );
            }
        },
        mounted() {
        }
    }
</script>