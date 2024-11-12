<template>
<div style="width: 100%;height: 100vh;display: flex;align-items: center;justify-content: center">
  <div>
    <div>
      输入sql语句自动生成文件
    </div>
    <n-input
        v-model:value="sql"
        style="width: 900px;margin-top: 4px"
        type="textarea"
        rows="12"
        :resizable="false"
        placeholder="请输入数据库sql"
    />
    <div style="margin-top: 4px;display: flex;justify-content: flex-end;gap: 12px">
      <n-button @click="generate('mybatis')" type="primary" secondary>
        Mybatis模板代码
      </n-button>
      <n-button @click="generate('mybatis-plus')" type="info" secondary>
        Mybatis-plus模板代码
      </n-button>
      <n-button @click="generate('mybatis-flex')" type="success" secondary>
        Mybatis-flex模板代码
      </n-button>
      <n-button @click="generate('jpa')" type="warning" secondary>
        SpringData-jpa模板代码
      </n-button>
    </div>
  </div>
</div>
</template>

<script setup>
  import {ref} from "vue";
  import axios from "axios";

  const sql = ref()

  const generate = (ormType) => {
    axios.post(`http://localhost:8080/generate/generatorEntity`, {
      'sql': sql.value,
      'ormType': ormType
    })
  }
</script>
