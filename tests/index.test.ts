import { expect, test, vi } from 'vitest'
import { instance } from '@mytiki/tiki-sdk-capacitor'

vi.mock('@mytiki/tiki-sdk-capacitor', async (importOriginal) => {
  return {
    instance: {
      initialize: () => true
    }
  }
})

test('adds 1 + 2 to equal 3', () => {
  expect(1+2).toBe(3)
})

test('initialize TIKI SDK', () => {
  expect(instance.initialize("a", "b")).toBeTruthy()
})
