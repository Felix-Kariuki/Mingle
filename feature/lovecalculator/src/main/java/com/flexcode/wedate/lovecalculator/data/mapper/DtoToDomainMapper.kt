/*
 * Copyright 2023 Felix Kariuki.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flexcode.wedate.lovecalculator.data.mapper

import com.flexcode.wedate.lovecalculator.data.dto.CalculatorResponseDto
import com.flexcode.wedate.lovecalculator.domain.model.CalculatorResponse

internal fun CalculatorResponseDto.toDomain(): CalculatorResponse {
    return CalculatorResponse(
        fname = fname,
        percentage = percentage,
        result = result,
        sname = sname
    )
}
