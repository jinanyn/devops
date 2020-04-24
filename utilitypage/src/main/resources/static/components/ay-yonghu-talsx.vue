<template>
    <div style="padding:10px;">
        <el-form :inline="true" :model="mySumbitForm" ref="mySumbitForm" label-width="100px"
                 :label-position="labelPosition" size="mini">
            <el-row>
                <el-col :span="16">
                    <el-form-item label="审查员代码" prop="shenchaydm">
                        <el-input v-model="mySumbitForm.shenchaydm" placeholder="输入审查员代码" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="16">
                    <el-form-item label="提案量上限值" prop="tavalue">
                        <el-input v-model="mySumbitForm.tavalue" placeholder="输入提案量上限值" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('mySumbitForm')">注销证书登录</el-button>
                        <el-button @click="resetForm('mySumbitForm')">重置</el-button>
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
                    redisKey: '',
                    redisVal: ''
                }
            }
        },
        methods: {
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            submitForm(formName) {
                let formData = new FormData();
                formData.append("shenchaydm", this.mySumbitForm.shenchaydm);
                formData.append("tavalue", this.mySumbitForm.tavalue);
                axios.post('/utility/ayOperate/updatescytasx', formData,).
                then(response => (console.log(response.data)))
                    .catch(function (error) { // 请求失败处理
                        console.log(error);
                    });
            }
        },
        mounted() {
        }
    }
</script>